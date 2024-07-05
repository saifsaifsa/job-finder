import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SkillsService {
    private apiUrl = 'http://localhost:8080/api/competences';

    constructor(private http: HttpClient) { }

    getSkills(): Observable<any> {
        return this.http.get(this.apiUrl);
    }

    updateSkills(id: number, skillsDetails: any): Observable<any> {
        return this.http.put(`${this.apiUrl}/${id}`, skillsDetails);
    }

    deleteSkills(id: number): Observable<any> {
        return this.http.delete(`${this.apiUrl}/${id}`);
    }

    addSkills(skillsDetails: any): Observable<any> {
        return this.http.post(this.apiUrl, skillsDetails);
    }
}