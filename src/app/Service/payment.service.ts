import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PaymentService {

  private base = 'http://localhost:9090';

  constructor(private http: HttpClient) {}

  createPaymentIntent(amount: number, description: string): Observable<any> {
    return this.http.post(`${this.base}/payment/create-intent`, {
      amount: amount * 100,
      currency: 'egp',
      description: description
    });
  }
}
