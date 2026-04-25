import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { CarService } from '../../Service/car.service';
import { Car } from '../Model/car';

@Component({
  selector: 'app-add-car',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent {
  car: Car = {} as Car;
  selectedFile?: File;
  previewUrl?: string;
  isLoading = false;

  constructor(private carService: CarService, private router: Router) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files?.[0]) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => (this.previewUrl = reader.result as string);
      reader.readAsDataURL(this.selectedFile);
    }
  }

  testData(): void {
    this.car = {
      brand: 'Toyota',
      name: 'Camry',
      color: 'Silver',
      type: 'Sedan',
      transmission: 'Automatic',
      description: 'Reliable family sedan',
      price: 500,
      buyPrice: 850000,
      year: 2023
    };
  }

  addCar(): void {
    this.isLoading = true;
    this.carService.addCar(this.car, this.selectedFile).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/cars']);
      },
      error: (err) => {
        this.isLoading = false;
        alert('Error: ' + (err.error?.message || err.message));
      }
    });
  }
}
