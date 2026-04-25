import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../Service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  messages: {messageEn: string, messageAr: string}[] = [];

  constructor(private authService: AuthService, private routes: Router) {}

  ngOnInit(): void {}

  login(username: string, password: string) {
    if (!this.validateAccount(username, password)) {
      setTimeout(() => { this.messages = []; }, 3000);
      return;
    }

    this.authService.login(username, password).subscribe(
      response => {
        sessionStorage.setItem("token", response.data.token);
        sessionStorage.setItem("roles", response.data.role);

        this.routes.navigateByUrl("/cars");
      },
      error => {
        if (Array.isArray(error.error)) {
          this.messages = error.error;
        } else {
          this.messages = [{
            messageEn: error.error.messageEn || "Login failed",
            messageAr: error.error.messageAr || "فشل تسجيل الدخول"
          }];
        }
        setTimeout(() => { this.messages = []; }, 3000);
      }
    );
  }

  validateAccount(username: string, password: string): boolean {
    if (!username) {
      this.messages = [{messageEn: "Username is required", messageAr: "اسم المستخدم مطلوب"}];
      return false;
    }
    if (!password) {
      this.messages = [{messageEn: "Password is required", messageAr: "كلمة المرور مطلوبة"}];
      return false;
    }
    return true;
  }
}
