import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cv } from './cv.model';

@Injectable({
  providedIn: 'root'
})
export class CvService {
  private apiUrl = 'http://localhost:8080/api/cv';

  constructor(private http: HttpClient) {}

  getAllCvs(): Observable<Cv[]> {
    return this.http.get<Cv[]>(`${this.apiUrl}`);
  }

  getCv(id: number): Observable<Cv> {
    return this.http.get<Cv>(`${this.apiUrl}/${id}`);
  }

  generateCv(): Observable<Cv> {
    return this.http.post<Cv>(`${this.apiUrl}/generate`, {});
  }

  updateCv(cv: Cv): Observable<Cv> {
    return this.http.put<Cv>(`${this.apiUrl}/${cv.id}`, cv);
  }

  deleteCv(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  downloadCvPdf(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}/download`, { responseType: 'blob' });
  }

  uploadCvPdf(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/${id}/upload`, formData);
  }
}
