import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BuyACar} from "../Model/buy-a-car";
import {CarService} from "../../Service/car.service";
import {AuthService} from "../../Service/auth.service";

@Component({
  selector: 'app-user-purchases',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-purchases.component.html',
  styleUrls: ['./user-purchases.component.css']
})
export class UserPurchasesComponent {
  purchases: BuyACar[] = [];

  constructor(
    private carService: CarService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.loadUserPurchases();
  }

  loadUserPurchases(): void {
    const userId = this.auth.getUserId();
    this.carService.getUserPurchases(userId).subscribe({
      next: (data) => this.purchases = data,
      error: (err) => console.error('Failed to load purchases', err)
    });
  }
}
