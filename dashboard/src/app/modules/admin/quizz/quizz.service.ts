import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class QuizzesService {
    private apiUrl = 'http://localhost:8080/api/quizzes';

    constructor(private http: HttpClient) { }

    getQuizzes(skilssId): Observable<any> {
        return this.http.get(this.apiUrl+'/skills/'+skilssId);
    }

    updateQuiz(id: number, quizzDetails: any): Observable<any> {
        return this.http.put(`${this.apiUrl}/${id}`, quizzDetails);
    }

    deleteQuizzes(id: number): Observable<any> {
        return this.http.delete(`${this.apiUrl}/${id}`);
    }

    addQuizzes(quizzDetails: any,competenceId:number): Observable<any> {
        return this.http.post(this.apiUrl+`/skills/${competenceId}`, quizzDetails);
    }
    getQuizById(id: number): Observable<any> {
        return this.http.get(`${this.apiUrl}/${id}`);
    }
    submitAnswers(id,answers): Observable<any> {
        return this.http.post(`${this.apiUrl}/${id}/submit`,answers)
    }
}
