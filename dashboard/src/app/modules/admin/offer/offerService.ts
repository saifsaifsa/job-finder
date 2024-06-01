import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class OfferService {
    private apiUrl = 'http://localhost:8080/api/offers';

    constructor(private http: HttpClient) { }

    getOffer(): Observable<any> {
        return this.http.get(this.apiUrl);
    }
}