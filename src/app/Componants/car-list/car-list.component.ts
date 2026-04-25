import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Car } from "../Model/car";
import { CarService } from "../../Service/car.service";
import { AuthService } from "../../Service/auth.service";
import { BookACar } from "../Model/book-a-car";

declare const bootstrap: any;

@Component({
  selector: 'app-car-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent implements OnInit {

  cars: Car[] = [];
  selectedCar: Car | null = null;
  rentFrom = '';
  rentTo = '';
  totalDays = 0;
  totalPrice = 0;

  constructor(
    private carService: CarService,
    public auth: AuthService,
    private router: Router  // ← أضف
  ) {}

  ngOnInit(): void {
    this.loadCars();
  }

  loadCars(): void {
    this.carService.getAllCars().subscribe(data => this.cars = data);
  }

  isUserLogin(): boolean {
    return this.auth.isUserLogin();
  }

  isAdmin(): boolean {
    return this.auth.isAdmin();
  }

  deleteCar(id: number): void {
    if (confirm('Delete this car?')) {
      this.carService.deleteCar(id).subscribe(() => this.loadCars());
    }
  }

  calcRent(): void {
    if (this.rentFrom && this.rentTo) {
      const from = new Date(this.rentFrom);
      const to = new Date(this.rentTo);
      const diff = Math.ceil((to.getTime() - from.getTime()) / (1000 * 60 * 60 * 24));
      this.totalDays = diff > 0 ? diff : 0;
      this.totalPrice = this.totalDays * (this.selectedCar?.price || 0);
    }
  }

  // ← غير buyCar علشان يروح لصفحة الدفع
  buyCar(carId: number, carName: string, buyPrice: number): void {
    const userId = this.auth.getUserId();
    if (!userId) {
      alert('Please login first');
      return;
    }
    this.router.navigate(['/payment'], {
      state: {
        carId: carId,
        amount: buyPrice,
        carName: carName
      }
    });
  }
}
