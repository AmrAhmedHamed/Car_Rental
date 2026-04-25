import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { loadStripe, Stripe, StripeCardElement, StripeElements } from '@stripe/stripe-js';
import { PaymentService } from '../../Service/payment.service';
import { CarService } from '../../Service/car.service';
import { AuthService } from '../../Service/auth.service';
import { BookACar } from '../Model/book-a-car';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  stripe: Stripe | null = null;
  elements: StripeElements | null = null;
  cardElement: StripeCardElement | null = null;

  carId: number = 0;
  amount: number = 0;
  carName: string = '';
  type: 'buy' | 'rent' = 'buy';
  isLoading = false;
  errorMsg = '';
  successMsg = '';

  constructor(
    private paymentService: PaymentService,
    private carService: CarService,
    private auth: AuthService,
    private router: Router
  ) {}

  async ngOnInit() {
    const state = history.state;
    this.carId = state.carId;
    this.amount = state.amount;
    this.carName = state.carName;
    this.type = state.type || 'buy';

    this.stripe = await loadStripe('pk_test_51TOcCPEJmKJaxS2krJV1QfbuUlBV2GrAjSIrtCpAntIuY5vHRIhFK0SmvUZYJeAhUz6Rr1QvxK5Afik2YzisonPn00YoNsbCQN');

    if (this.stripe) {
      this.elements = this.stripe.elements();
      this.cardElement = this.elements.create('card', {
        style: {
          base: {
            fontSize: '16px',
            color: '#333',
            '::placeholder': { color: '#aaa' }
          }
        }
      });
      this.cardElement.mount('#card-element');
    }
  }

  async pay() {
    if (!this.stripe || !this.cardElement) return;
    this.isLoading = true;
    this.errorMsg = '';

    const description = this.type === 'rent' ? `Rent ${this.carName}` : `Buy ${this.carName}`;
    this.paymentService.createPaymentIntent(this.amount, description).subscribe({
      next: async (response) => {
        const result = await this.stripe!.confirmCardPayment(response.clientSecret, {
          payment_method: { card: this.cardElement! }
        });

        if (result.error) {
          this.errorMsg = result.error.message || 'Payment failed';
          this.isLoading = false;
        } else if (result.paymentIntent.status === 'succeeded') {
          const userId = this.auth.getUserId();
          if (!userId) {
            this.errorMsg = 'User not logged in';
            this.isLoading = false;
            return;
          }

          const paymentIntentId = result.paymentIntent.id;

          if (this.type === 'buy') {
            this.carService.buyCar(userId, this.carId, paymentIntentId).subscribe({
              next: () => {
                this.successMsg = 'Payment successful! Purchase completed.';
                this.isLoading = false;
                setTimeout(() => this.router.navigate(['/my-purchases']), 2000);
              },
              error: () => {
                this.errorMsg = 'Payment done but purchase save failed';
                this.isLoading = false;
              }
            });
          } else if (this.type === 'rent') {
            const pending = sessionStorage.getItem('pendingRent');
            if (!pending) {
              this.errorMsg = 'Missing booking data';
              this.isLoading = false;
              return;
            }
            const bookingData = JSON.parse(pending);
            const booking: BookACar = {
              carId: this.carId,
              fromDate: bookingData.fromDate,
              toDate: bookingData.toDate,
              days: bookingData.days,
              price: bookingData.totalPrice,
              paymentIntentId: paymentIntentId,
              userId: userId,                    // ✅ إضافة userId
              bookCarStatus: 'PENDING'           // ✅ إضافة الحالة
            };
            this.carService.bookCar(userId, booking).subscribe({
              next: () => {
                sessionStorage.removeItem('pendingRent');
                this.successMsg = 'Payment successful! Booking completed.';
                this.isLoading = false;
                setTimeout(() => this.router.navigate(['/my-bookings']), 2000);
              },
              error: () => {
                this.errorMsg = 'Payment done but booking save failed';
                this.isLoading = false;
              }
            });
          }
        }
      },
      error: () => {
        this.errorMsg = 'Failed to create payment';
        this.isLoading = false;
      }
    });
  }
}
