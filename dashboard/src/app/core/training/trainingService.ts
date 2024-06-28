import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, ReplaySubject } from 'rxjs';
import { Training } from './training.types';
import { environment } from "environments/environment";
import { map } from 'rxjs/internal/operators/map';

@Injectable({
    providedIn: 'root'
})
export class TrainingService {

    private _training: ReplaySubject<Training> = new ReplaySubject<Training>(1);

    /**
     * Constructor
     */
    constructor(private _httpClient: HttpClient) { }

    /**
     * Getter and Setter
     */
    get training$() : Observable<Training> {
        return this._training.asObservable();
    }

    set training(value : Training) {
        this._training.next(value);
    }

    /**
     * public method
     */

    getTrainings(): Observable<Training[]>{
        return this._httpClient.get<Training[]>(`${environment.apiUrl}training`).pipe(
            map(data =>{
                return data;
            })
        )
    }

    getFilteredTrainings(category): Observable<Training[]>{
        return this._httpClient.get<Training[]>(`${environment.apiUrl}training/findTrainingByCategories/${category}`).pipe(
            map(data =>{
                return data;
            })
        )
    }

    getTraining(id:string){
        return this._httpClient.get(`${environment.apiUrl}training/${id}`)
    }
    
    /**
    * Update the training
    *
    * @param data @param id 
    */
    updateTraining(id:string,data:any){
        let headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this._httpClient.put(`${environment.apiUrl}training`,data,{headers: headers})
    }
    
    /**
    * delete the training
    *
    * @param id
    */
    deleteTraining(id:string){
    return this._httpClient.delete(`${environment.apiUrl}training/${id}`)
    }
    
    /**
    * Create the training
    *
    * @param data 
    */    
    createTraining(data:any){
        let headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this._httpClient.post(`${environment.apiUrl}training`,data,{headers: headers})
    }
}