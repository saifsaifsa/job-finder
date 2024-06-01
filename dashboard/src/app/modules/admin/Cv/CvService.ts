import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cv } from '../models/cv';

@Injectable({
    providedIn: 'root'
})
export class CvService {
    private apiUrl = 'http://localhost:8080/api/cv';

    constructor(private http: HttpClient) {}

    getCvs(): Observable<Cv[]> {
        return this.http.get<Cv[]>(this.apiUrl);
    }

    getCv(id: number): Observable<Cv> {
        return this.http.get<Cv>(`${this.apiUrl}/${id}`);
    }

    createCv(cv: Cv): Observable<Cv> {
        return this.http.post<Cv>(this.apiUrl, cv);
    }

    updateCv(cv: Cv): Observable<Cv> {
        return this.http.put<Cv>(this.apiUrl, cv);
    }

    deleteCv(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    incrementViews(id: number): Observable<void> {
        return this.http.get<void>(`${this.apiUrl}/${id}/increment-views`);
    }

    exportCvToPDF(id: number): Observable<Blob> {
        return this.http.get(`${this.apiUrl}/${id}/export-pdf`, { responseType: 'blob' });
    }
}
