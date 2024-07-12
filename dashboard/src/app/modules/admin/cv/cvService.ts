import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CvService {
  private apiUrl = 'http://localhost:8080/api/cv';

  constructor(private http: HttpClient) { }

 
  getAllCvs(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getCv(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createCv(cvData: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<any>(this.apiUrl, cvData, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  updateCv(cv: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${cv.id}`, cv);
  }

  deleteCv(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  incrementViews(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}/increment-views`);
  }

  exportCvToPDF(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}/export-pdf`, { responseType: 'blob' }).pipe(
      catchError(this.handleError),
      tap(() => this.incrementDownloads(id)) // Use tap here to increment downloads after successful export
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    }
    return throwError('Something bad happened; please try again later.');
  }

  private incrementDownloads(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}/increment-downloads`);
  }
}
