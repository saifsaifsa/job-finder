import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthUtils } from 'app/core/auth/auth.utils';
import { UserService } from 'app/core/user/user.service';
import { environment } from 'environments/environment';
import { Observable, of, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

@Injectable()
export class AuthService {
    private _authenticated: boolean = false;

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
        return this._httpClient.post('api/auth/forgot-password', email);
    }

    /**
     * Reset password
     *
     * @param password
     */
    resetPassword(password: string): Observable<any> {
        return this._httpClient.post('api/auth/reset-password', password);
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
                    if (user.role[0].authority !== "ROLE_ADMIN") {
                        return throwError('User role is not authorized.'); // Throw an error if the user's role is not 99
                    }
                    user.role = user.role[0].authority
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
        name: string;
        email: string;
        password: string;
        company: string;
    }): Observable<any> {
        return this._httpClient.post('api/auth/sign-up', user);
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
}