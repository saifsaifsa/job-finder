import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cv } from './cv.model';

@Injectable({
  providedIn: 'root'
})
export class CvService {
  private apiUrl = 'http://localhost:8080/api/cv';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllCvs(): Observable<Cv[]> {
    return this.http.get<Cv[]>(`${this.apiUrl}`, { headers: this.getAuthHeaders() });
  }

  getCv(id: number): Observable<Cv> {
    return this.http.get<Cv>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  generateCv(): Observable<Cv> {
    const newCv: Partial<Cv> = {
      content: '',
      views: 0,
      downloads: 0,
      linkedInData: '',
      skills: []
    };
    return this.http.post<Cv>(this.apiUrl, newCv, { headers: this.getAuthHeaders() });
  }

  updateCv(cv: Cv): Observable<Cv> {
    return this.http.put<Cv>(`${this.apiUrl}/${cv.id}`, cv, { headers: this.getAuthHeaders() });
  }

  deleteCv(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  downloadCvPdf(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}/download`, { headers: this.getAuthHeaders(), responseType: 'blob' });
  }

  uploadCvPdf(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/upload?userId=`+id, formData, { headers: this.getAuthHeaders() });
  }
}
