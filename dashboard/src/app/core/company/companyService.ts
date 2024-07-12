import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, ReplaySubject } from 'rxjs';
import { Company } from './company.types';
import { environment } from "environments/environment";
import { map } from 'rxjs/internal/operators/map';

@Injectable({
    providedIn: 'root'
})
export class CompanyService {

    private _company: ReplaySubject<Company> = new ReplaySubject<Company>(1);

    /**
     * Constructor
     */
    constructor(private _httpClient: HttpClient) { }

    /**
     * Getter and Setter
     */
    get company$() : Observable<Company> {
        return this._company.asObservable();
    }

    set company(value : Company) {
        this._company.next(value);
    }

    /**
     * public method
     */

    geCompanies(): Observable<Company[]>{
        return this._httpClient.get<Company[]>(`${environment.apiUrl}company`).pipe(
            map(data =>{
                return data;
            })
        )
    }

    getAllCompanies(page: number = 0, size: number = 10, filters: { [key: string]: string } = {},
        sortBy: string = '', sortOrder: 'asc' | 'desc' = 'asc'): Observable<any> {
        let params = new HttpParams();
    
        params = params.append('page', page.toString());
        params = params.append('size', size.toString());
    
        Object.keys(filters).forEach((key) => {
          if (filters[key] !== '') {
            params = params.append(key, filters[key]);
          }
        });
        
    
        if (sortBy !== '' && (sortOrder === 'asc' || sortOrder === 'desc')) {
          params = params.append('sortBy', sortBy);
          params = params.append('sortOrder', sortOrder);
        }
    
        return this._httpClient.get(`${environment.apiUrl}company`, { params });
      }



    getCompany(id:string){
        return this._httpClient.get(`${environment.apiUrl}company/${id}`)
    }
    
    /**
    * Update the training
    *
    * @param data @param id 
    */
    updateCompany(id:string,data:any){
        // let headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this._httpClient.put(`${environment.apiUrl}company/${id}`,data)
    }
    
    /**
    * delete the training
    *
    * @param id
    */
    deleteCompany(id:string){
    return this._httpClient.delete(`${environment.apiUrl}company/${id}`)
    }
    
    /**
    * Create the training
    *
    * @param data 
    */    
    createCompany(data:any){
        // let headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this._httpClient.post(`${environment.apiUrl}company`,data)
    }
    rateCompany(postId: Number): Observable<any> {
        return this._httpClient.post<any>(`${environment.apiUrl}company/${postId}/rate`, {});
      }
    

}