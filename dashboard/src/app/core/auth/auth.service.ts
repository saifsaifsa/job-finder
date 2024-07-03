import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthUtils } from 'app/core/auth/auth.utils';
import { UserService } from 'app/core/user/user.service';
import { environment } from 'environments/environment';
import { Observable, of, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

@Injectable()
export class AuthService {
    private _authenticated: boolean = false;
    private fileUrl = 'http://localhost:8080/api/files/';

    /**
     * Constructor
     */
    constructor(
        private _httpClient: HttpClient,
        private _userService: UserService
    ) {}

    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------

    /**
     * Setter & getter for access token
     */
    set accessToken(token: string) {
        localStorage.setItem('accessToken', token);
    }

    get accessToken(): string {
        return localStorage.getItem('accessToken') ?? '';
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Forgot password
     *
     * @param email
     */
    forgotPassword(email: string): Observable<any> {
        return this._httpClient.post(`${environment.baseUrl}api/auth/forgot-password`, {email});
    }

    /**
     * Reset password
     *
     * @param password
     */
    resetPassword(newPassword: string,token:string): Observable<any> {
        return this._httpClient.post(`${environment.baseUrl}api/auth/reset-password`, {newPassword,token});
    }

    decodeJwt(token: string): any {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const decodedData = atob(base64);
        const decodedToken = JSON.parse(decodedData);
        return decodedToken;
    }

    /**
     * Sign in
     *
     * @param credentials
     */
    signIn(credentials: { email: string; password: string }): Observable<any> {    
        if (this._authenticated) {
            return throwError('User is already logged in.');
        }
        return this._httpClient
            .post(`${environment.baseUrl}api/auth/signin`, credentials)
            .pipe(
                switchMap((response: any) => {

                    const  user  = this.decodeJwt(response.accessToken);                    
                    // if (user.role[0].authority !== "ROLE_ADMIN") {
                    //     return throwError('User role is not authorized.'); // Throw an error if the user's role is not 99
                    // }
                    user.role = user.role[0].authority
                    user.avatar =  this.fileUrl+user.profilePicture
                    localStorage.setItem("user",JSON.stringify(user))
                    this.accessToken = response.accessToken;
                    this._authenticated = true;
                    this._userService.setLoggedInUser(user)
                    this._userService.user = user;
                    return of(user);
                }),
                catchError((error) => {
                    // Handle the error
                    console.error('Login error:', error);
                    // You can show a notification to the user, log the error, or re-throw it
                    return throwError(error);
                })
            );
    }

    /**
     * Sign in using the access token
     */
    signInUsingToken(): Observable<any> {
        // Renew token
        return this._httpClient
            .post(environment.baseUrl + 'auth/refresh-token', {
                accessToken: this.accessToken,
            })
            .pipe(
                catchError(() =>
                    // Return false
                    of(false)
                ),
                switchMap((response: any) => {

                    const { user } = this.decodeJwt(response.accessToken);
                    if (user.role !== 99) {
                        return throwError('User role is not authorized.');
                    }
                    this.accessToken = response.accessToken;

                    this._authenticated = true;
                    this._userService.setLoggedInUser(user)

                    this._userService.user = response.user;

                    return of(true);
                })
            );
    }

    /**
     * Sign out
     */
    signOut(): Observable<any> {
        // Remove the access token from the local storage
        localStorage.removeItem('accessToken');

        // Set the authenticated flag to false
        this._authenticated = false;

        // Return the observable
        return of(true);
    }

    /**
     * Sign up
     *
     * @param user
     */
    signUp(user: {
        username: string;
        email: string;
        password: string;
        company: string;
        role: string;
    }): Observable<any> {
        return this._httpClient.post(`${environment.baseUrl}api/auth/signup`, user);
    }

    /**
     * Unlock session
     *
     * @param credentials
     */
    unlockSession(credentials: {
        email: string;
        password: string;
    }): Observable<any> {
        return this._httpClient.post('api/auth/unlock-session', credentials);
    }

    /**
     * Check the authentication status
     */
    check(): Observable<boolean> {
        // Check if the user is logged in
        if (this._authenticated) {
            return of(true);
        }

        // Check the access token availability
        if (!this.accessToken) {
            return of(false);
        }

        // Check the access token expire date
        if (
            this.accessToken == undefined ||
            AuthUtils.isTokenExpired(this.accessToken)
        ) {
            return of(false);
        }

        // If the access token exists and it didn't expire, sign in using it
        const user = JSON.parse(localStorage.getItem("user"))
        this._authenticated = true;
        this._userService.setLoggedInUser(user)
        this._userService.user = user;
        return of(true);
    }

    login(credentials: { email: string; password: string }) {
        // Throw error, if the user is already logged in
        if (this._authenticated) {
            return throwError('User is already logged in.');
        }
        return this._httpClient.post(
            `${environment.baseUrl}auth/login`,
            credentials
        );
    }

    confirmation(token:string) {   
        return this._httpClient.get(`${environment.baseUrl}api/auth/verify?token=${token}`);
    }


    linkedInLogin() {
        const clientId = '77lz7wunm0s5jn';
        const redirectUri = encodeURIComponent(window.location.origin + '/callback');
        const scope = 'openid profile email';
        const responseType = 'code';
        const state = 'random_string';

        const url = `https://www.linkedin.com/oauth/v2/authorization?response_type=${responseType}&client_id=${clientId}&redirect_uri=${redirectUri}&scope=${scope}&state=${state}`;

        const width = 600;
        const height = 600;
        const left = (window.screen.width / 2) - (width / 2);
        const top = (window.screen.height / 2) - (height / 2);

        const popup = window.open(url, 'LinkedIn Login', `width=${width},height=${height},top=${top},left=${left}`);

        window.addEventListener('message', (event) => {
            if (event.origin === window.location.origin) {
                const code = event.data.code;
                this.exchangeCodeForToken(code);
            }
        });
    }

    exchangeCodeForToken(code: string) {
        const params = new HttpParams({
            fromObject: {
                client_id: '77lz7wunm0s5jn',
                client_secret: '24uSXbMPN9VSXjPm',
                redirect_uri: window.location.origin + '/callback',
                grant_type: 'authorization_code',
                code: code,
            }
        });

        this._httpClient.post('http://localhost:8080/api/auth/linkedin', params.toString(), {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).subscribe((response: any) => {
            console.log("response: ",response);
            alert(JSON.stringify(response))
        });
    }
}