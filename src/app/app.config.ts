import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {LoginComponent} from "./Componants/login/login.component";
import {SignupComponent} from "./Componants/signup/signup.component";
import {CarListComponent} from "./Componants/car-list/car-list.component";
import {AddCarComponent} from "./Componants/add-car/add-car.component";
import {EditCarComponent} from "./Componants/edit-car/edit-car.component";
import {AdminBookingsComponent} from "./Componants/admin-bookings/admin-bookings.component";
import {AdminPurchasesComponent} from "./Componants/admin-purchases/admin-purchases.component";
import {UserBookingsComponent} from "./Componants/user-bookings/user-bookings.component";
import {UserPurchasesComponent} from "./Componants/user-purchases/user-purchases.component";
import {AuthGuard} from "./Componants/guard/auth.guard";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {HeaderComponent} from "./Componants/header/header/header.component";
import {AuthInterceptor} from "./interceptor/core/auth.interceptor";
import {CarDetailComponent} from "./Componants/car-detail/car-detail.component";
import {PaymentComponent} from "./Componants/payment/payment.component";

const routes: Routes = [
  { path: 'cars', component: CarListComponent },
  { path: 'car/:id', component: CarDetailComponent },
  { path: 'add-car', component: AddCarComponent, canActivate: [AuthGuard] },
  { path: 'edit-car/:id', component: EditCarComponent, canActivate: [AuthGuard] },
  { path: 'admin/bookings', component: AdminBookingsComponent, canActivate: [AuthGuard] },
  { path: 'admin/purchases', component: AdminPurchasesComponent, canActivate: [AuthGuard] },
  { path: 'my-bookings', component: UserBookingsComponent, canActivate: [AuthGuard] },
  { path: 'my-purchases', component: UserPurchasesComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: '', redirectTo: '/cars', pathMatch: 'full' },
  { path: 'payment', component: PaymentComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule, // ✅ الصح
    HeaderComponent,
    AdminPurchasesComponent,
    AdminBookingsComponent,
    AddCarComponent,
    CarListComponent,
    UserPurchasesComponent,
    UserBookingsComponent,
    EditCarComponent,
    PaymentComponent
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
