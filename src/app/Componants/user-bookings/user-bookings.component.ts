import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CarService } from '../../Service/car.service';
import { BookACar } from '../Model/book-a-car';
import { AuthService } from '../../Service/auth.service';

@Component({
  selector: 'app-user-bookings',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './user-bookings.component.html',
  styleUrls: ['./user-bookings.component.css'],
  encapsulation: ViewEncapsulation.None  // ← أضف السطر ده
})
export class UserBookingsComponent implements OnInit {
  bookings: BookACar[] = [];

  constructor(
    private carService: CarService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.loadUserBookings();
  }

  loadUserBookings(): void {
    const userId = this.auth.getUserId();
    if (userId) {
      this.carService.getUserBookings(userId).subscribe({
        next: (data) => this.bookings = data,
        error: (err) => console.error('Failed to load bookings', err)
      });
    }
  }
}
