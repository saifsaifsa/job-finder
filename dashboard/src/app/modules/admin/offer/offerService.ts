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

    updateOffer(id: number, offerDetails: any): Observable<any> {
        return this.http.put(`${this.apiUrl}/${id}`, offerDetails);
    }

    deleteOffer(id: number): Observable<any> {
        return this.http.delete(`${this.apiUrl}/${id}`);
    }

    addOffer(offerDetails: any): Observable<any> {
        offerDetails.competences = offerDetails.competences[0];
        return this.http.post(this.apiUrl, offerDetails);
    }
}