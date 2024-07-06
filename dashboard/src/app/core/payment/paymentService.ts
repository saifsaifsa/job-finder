// src/app/services/payment.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private _httpClient: HttpClient) { }

  initiatePayment(paymentRequest: any): Observable<any> {
    let headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this._httpClient.post(`${environment.apiUrl}payment/checkout`, paymentRequest);
  }
}
