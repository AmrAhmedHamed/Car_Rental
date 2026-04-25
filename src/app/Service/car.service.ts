import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../Componants/Model/car';
import { BookACar } from '../Componants/Model/book-a-car';
import { BuyACar } from '../Componants/Model/buy-a-car';

@Injectable({ providedIn: 'root' })
export class CarService {
  private base = 'http://localhost:9090';

  constructor(private http: HttpClient) {}

  // ==================== CAR CRUD ====================

  private buildFormData(car: Car, imageFile?: File): FormData {
    const fd = new FormData();
    fd.append('car', new Blob([JSON.stringify(car)], { type: 'application/json' }));
    if (imageFile) {
      fd.append('image', imageFile, imageFile.name);
    }
    return fd;
  }

  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.base}/cars/all`);
  }

  getCarById(id: number): Observable<Car> {
    return this.http.get<Car>(`${this.base}/cars/${id}`);
  }

  addCar(car: Car, imageFile?: File): Observable<Car> {
    return this.http.post<Car>(`${this.base}/cars/add`, this.buildFormData(car, imageFile));
  }

  updateCar(id: number, car: Car, imageFile?: File): Observable<Car> {
    return this.http.put<Car>(`${this.base}/cars/update/${id}`, this.buildFormData(car, imageFile));
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/cars/delete/${id}`);
  }

  // ==================== BOOK (RENT) ====================

  bookCar(userId: number, booking: BookACar): Observable<void> {
    return this.http.post<void>(`${this.base}/bookings/book/${userId}`, booking);
  }

  getUserBookings(userId: number): Observable<BookACar[]> {
    return this.http.get<BookACar[]>(`${this.base}/bookings/user/${userId}`);
  }

  getCarBookings(carId: number): Observable<BookACar[]> {
    return this.http.get<BookACar[]>(`${this.base}/bookings/car/${carId}`);
  }

  getAllBookings(): Observable<BookACar[]> {
    return this.http.get<BookACar[]>(`${this.base}/bookings/all`);
  }

  updateBookingStatus(bookingId: number, status: string): Observable<void> {
    return this.http.put<void>(`${this.base}/bookings/status/${bookingId}?status=${status}`, {});
  }

  deleteBooking(bookingId: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/bookings/delete/${bookingId}`);
  }

  // ==================== BUY ====================

  buyCar(userId: number, carId: number, paymentIntentId: string): Observable<void> {
    return this.http.post<void>(
      `${this.base}/purchases/buy/${userId}/${carId}?paymentIntentId=${paymentIntentId}`,
      {}
    );
  }

  getUserPurchases(userId: number): Observable<BuyACar[]> {
    return this.http.get<BuyACar[]>(`${this.base}/purchases/user/${userId}`);
  }

  getCarPurchases(carId: number): Observable<BuyACar[]> {
    return this.http.get<BuyACar[]>(`${this.base}/purchases/car/${carId}`);
  }

  getAllPurchases(): Observable<BuyACar[]> {
    return this.http.get<BuyACar[]>(`${this.base}/purchases/all`);
  }

  updatePurchaseStatus(purchaseId: number, status: string): Observable<void> {
    return this.http.put<void>(`${this.base}/purchases/status/${purchaseId}?status=${status}`, {});
  }

  deletePurchase(purchaseId: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/purchases/delete/${purchaseId}`);
  }
}
