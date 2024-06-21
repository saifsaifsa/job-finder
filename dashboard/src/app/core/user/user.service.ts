import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from 'app/core/user/user.types';
import { environment } from 'environments/environment';
import { BehaviorSubject, Observable, ReplaySubject } from 'rxjs';
import { map, tap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class UserService
{
    private userSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
    private _user: ReplaySubject<User> = new ReplaySubject<User>(1);
    private _loggedInUser: Observable<User | null> = this.userSubject.asObservable();


    /**
     * Constructor
     */
    constructor(private _httpClient: HttpClient)
    {
    }

    setLoggedInUser(user: User): void {
        this.userSubject.next(user);
        this._user.next(user);
      }
    
      updateLoggedInUser(user: User): void {
        this.userSubject.next(user);
      }
    
      getLoggedInUser(): Observable<User | null> {
        return this._loggedInUser;
      }


    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------

    /**
     * Setter & getter for user
     *
     * @param value
     */
    set user(value: User)
    {
        // Store the value
        this._user.next(value);
    }

    get user$(): Observable<User>
    {
        return this._user.asObservable();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Get the current logged in user data
     */
    get(): Observable<User>
    {
        return this._httpClient.get<User>('api/common/user').pipe(
            tap((user) => {
                this._user.next(user);
            })
        );
    }

    /**
     * Update the user
     *
     * @param user
     */
    update(user: User): Observable<any>
    {
        return this._httpClient.patch<User>('api/common/user', {user}).pipe(
            map((response) => {
                this._user.next(response);
            })
        );
    }
    getUsers(
        page: number = 1,
        pageSize: number = 10,
        filters: { [key: string]: string } = {},
        sortField: string = '',
        sortOrder: 'asc' | 'desc' = 'asc'
      ): Observable<any> {
        let params = new HttpParams();
    
        params = params.append('page', page.toString());
        params = params.append('pageSize', pageSize.toString());
    
        Object.keys(filters).forEach((key) => {
          if (filters[key] !== '') {
            params = params.append(key, filters[key]);
          }
        });
    
        if (sortField !== '' && (sortOrder === 'asc' || sortOrder === 'desc')) {
          params = params.append('sortField', sortField);
          params = params.append('sortOrder', sortOrder);
        }
    
        return this._httpClient.get(`${environment.baseUrl}api/users`, { params });
      }
    deleteUser(id:string){
        return this._httpClient.delete(`${environment.baseUrl}api/users/${id}`)
    }
    getUser(id:string){
        return this._httpClient.get(`${environment.baseUrl}api/users/${id}`)
    }
    updateUser(id:string,data:any){
        return this._httpClient.put(`${environment.baseUrl}api/users/${id}`,data)
    }
    addUser(data:any){
        return this._httpClient.post(`${environment.baseUrl}api/users`,data)
    }
    toggleConfirmation(id:string,confirmed:boolean){
        return this._httpClient.patch(`${environment.baseUrl}api/users/${id}/confirmed`,{confirmed})
    }
}