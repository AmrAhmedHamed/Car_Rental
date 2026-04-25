import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { CarService } from '../../Service/car.service';
import { Car } from '../Model/car';

@Component({
  selector: 'app-edit-car',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './edit-car.component.html',
  styleUrls: ['./edit-car.component.css']
})
export class EditCarComponent implements OnInit {

  car: Car = {} as Car;

  selectedFile?: File;
  previewUrl?: string;

  isLoading = false;

  constructor(
    private carService: CarService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.carService.getCarById(id).subscribe(data => {
        this.car = data;
      });
    }
  }

  // 🔥 نفس بتاع add
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files?.[0]) {
      this.selectedFile = input.files[0];

      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result as string;
      };

      reader.readAsDataURL(this.selectedFile);
    }
  }

  updateCar(): void {
    if (!this.car.id) return;

    this.isLoading = true;

    this.carService.updateCar(this.car.id, this.car, this.selectedFile)
      .subscribe({
        next: () => {
          this.isLoading = false;
          alert('Car updated successfully');
          this.router.navigate(['/cars']);
        },
        error: (err) => {
          console.error(err);
          this.isLoading = false;
          alert('Update failed');
        }
      });
  }
}
