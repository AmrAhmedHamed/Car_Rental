import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BuyACar} from "../Model/buy-a-car";
import {CarService} from "../../Service/car.service";

@Component({
  selector: 'app-admin-purchases',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-purchases.component.html',
  styleUrls: ['./admin-purchases.component.css']
})
export class AdminPurchasesComponent {

  purchases: BuyACar[] = [];

  constructor(private carService: CarService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.carService.getAllPurchases().subscribe({
      next: (data) => this.purchases = data,
      error: (err) => console.error('Failed to load purchases', err)
    });
  }

  updateStatus(id: number, status: string): void {
    this.carService.updatePurchaseStatus(id, status).subscribe({
      next: () => this.load(),
      error: (err) => console.error('Status update failed', err)
    });
  }

  deletePurchase(id: number): void {
    if (confirm('Are you sure you want to delete this purchase record?')) {
      this.carService.deletePurchase(id).subscribe({
        next: () => this.load(),
        error: (err) => console.error('Delete failed', err)
      });
    }
  }
}
