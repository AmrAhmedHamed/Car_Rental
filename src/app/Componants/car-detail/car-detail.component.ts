import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CarService } from '../../Service/car.service';
import { AuthService } from '../../Service/auth.service';
import { Car } from '../Model/car';

@Component({
  selector: 'app-car-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './car-detail.component.html',
  styleUrls: ['./car-detail.component.css']
})
export class CarDetailComponent implements OnInit {

  car: Car | null = null;
  rentFrom = '';
  rentTo = '';
  totalDays = 0;
  totalPrice = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private carService: CarService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.carService.getCarById(id).subscribe(data => this.car = data);
  }

  calcRent(): void {
    if (this.rentFrom && this.rentTo) {
      const from = new Date(this.rentFrom);
      const to = new Date(this.rentTo);
      const diff = Math.ceil((to.getTime() - from.getTime()) / (1000 * 60 * 60 * 24));
      this.totalDays = diff > 0 ? diff : 0;
      this.totalPrice = this.totalDays * (this.car?.price || 0);
    }
  }

  // 🔹 استبدال الحجز المباشر بالانتقال إلى صفحة الدفع مع نوع العملية rent
  proceedToRent(): void {
    if (!this.auth.isUserLogin()) {
      alert('Please login first');
      return;
    }
    if (!this.rentFrom || !this.rentTo || this.totalDays <= 0) {
      alert('Please select valid dates');
      return;
    }

    // حفظ بيانات الحجز في sessionStorage لاستخدامها بعد الدفع
    const bookingData = {
      carId: this.car!.id,
      fromDate: this.rentFrom,
      toDate: this.rentTo,
      days: this.totalDays,
      totalPrice: this.totalPrice
    };
    sessionStorage.setItem('pendingRent', JSON.stringify(bookingData));

    // الانتقال إلى صفحة الدفع مع تحديد نوع العملية rent
    this.router.navigate(['/payment'], {
      state: {
        carId: this.car!.id,
        amount: this.totalPrice,
        carName: this.car!.brand + ' ' + this.car!.name,
        type: 'rent'   // 🔹 تحديد النوع
      }
    });
  }
}
