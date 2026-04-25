import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../Service/auth.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  messages: {messageEn: string, messageAr: string}[] = [];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  CreateAccount(Username: string, Password: string, ConfirmPassword: string, Age: number) {
    if (!this.validateAccount(Username, Password, ConfirmPassword, Age)) {
      setTimeout(() => { this.messages = []; }, 3000);
      return;
    }

    this.authService.createAccount(Username, Password, Age).subscribe(
      response => {
        sessionStorage.setItem("token", response.data.token);
        sessionStorage.setItem("roles", response.data.role);
        this.router.navigateByUrl("/cars");
      },
      error => {
        if (Array.isArray(error.error)) {
          this.messages = error.error;
        } else {
          this.messages = [{
            messageEn: error.error.messageEn || "An error occurred",
            messageAr: error.error.messageAr || "حدث خطأ"
          }];
        }
        setTimeout(() => { this.messages = []; }, 3000);
      }
    );
  }

  validateAccount(username: string, password: string, confirmPassword: string, Age: number): boolean {
    if (!username) {
      this.messages = [{messageEn: "Username is required", messageAr: "اسم المستخدم مطلوب"}];
      return false;
    }
    if (!password) {
      this.messages = [{messageEn: "Password is required", messageAr: "كلمة المرور مطلوبة"}];
      return false;
    }
    if (!confirmPassword) {
      this.messages = [{messageEn: "Confirm password is required", messageAr: "تأكيد كلمة المرور مطلوب"}];
      return false;
    }
    if (password !== confirmPassword) {
      this.messages = [{messageEn: "Passwords do not match", messageAr: "كلمة المرور وتأكيدها غير متطابقين"}];
      return false;
    }
    if (!Age) {
      this.messages = [{messageEn: "Age is required", messageAr: "العمر مطلوب"}];
      return false;
    }
    return true;
  }
}
