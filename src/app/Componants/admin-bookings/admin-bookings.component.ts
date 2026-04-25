import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BookACar} from "../Model/book-a-car";
import {CarService} from "../../Service/car.service";

@Component({
  selector: 'app-admin-bookings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-bookings.component.html',
  styleUrls: ['./admin-bookings.component.css']
})
export class AdminBookingsComponent {

  bookings: BookACar[] = [];

  constructor(private carService: CarService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.carService.getAllBookings().subscribe(data => this.bookings = data);
  }

  updateStatus(id: number, status: string): void {
    this.carService.updateBookingStatus(id, status).subscribe(() => this.load());
  }

  deleteBooking(id: number): void {
    if (confirm('Delete this booking?'))
      this.carService.deleteBooking(id).subscribe(() => this.load());
  }
}
