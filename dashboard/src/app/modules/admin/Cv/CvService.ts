import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class CvService {
    private apiUrl = 'http://localhost:8080/api/cv'; 

    constructor(private http: HttpClient) { }
    

    getAllCvs(): Observable<any> { 
        return this.http.get<any>(this.apiUrl);
    }

    getCv(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${id}`);
    }

    createCv(cvData: any): Observable<any> {
    return this.http.post(this.apiUrl, cvData);
  }

    updateCv(cv: any): Observable<any> {
        return this.http.put<any>(this.apiUrl, cv);
    }

    deleteCv(id: number): Observable<any> {
        return this.http.delete<any>(`${this.apiUrl}/${id}`);
    }

    incrementViews(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${id}/increment-views`);
    }

    incrementDownloads(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${id}/increment-downloads`);
    }

    exportCvToPDF(id: number): Observable<any> {
        return this.http.get(`${this.apiUrl}/${id}/export-pdf`, { responseType: 'blob' });
    }
}
