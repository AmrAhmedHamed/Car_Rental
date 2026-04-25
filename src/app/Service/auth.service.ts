import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:9090/auth';

  constructor(private http: HttpClient) {}

  createAccount(username: string, password: string, age: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/signup`, { username, password, age })
      .pipe(
        tap(response => {
          // Store token, role, and userId after successful signup
          if (response.token) {
            sessionStorage.setItem("token", response.token);
            sessionStorage.setItem("roles", response.role);
            const userId = this.extractUserIdFromToken(response.token);
            if (userId) sessionStorage.setItem("userId", userId.toString());
          }
        }),
        map(response => ({ success: true, data: response })),
        catchError(error => throwError(() => error))
      );
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/login`, { username, password })
      .pipe(
        tap(response => {
          if (response.token) {
            sessionStorage.setItem("token", response.token);
            sessionStorage.setItem("roles", response.role);
            const userId = this.extractUserIdFromToken(response.token);
            if (userId) sessionStorage.setItem("userId", userId.toString());
          }
        }),
        map(response => ({ success: true, data: response })),
        catchError(error => throwError(() => error))
      );
  }

  // Extract userId from JWT token
  private extractUserIdFromToken(token: string): number | null {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.userId || payload.id || null;
    } catch {
      return null;
    }
  }

  getUserId(): number {
    const id = sessionStorage.getItem("userId");
    return id ? Number(id) : 0;
  }

  isUserLogin(): boolean {
    return sessionStorage.getItem("token") != null &&
      sessionStorage.getItem("token") != undefined;
  }

  isAdmin(): boolean {
    const roles = sessionStorage.getItem("roles");
    if (!roles) return false;
    return roles.includes("ROLE_ADMIN");
  }

  logOut() {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("roles");
    sessionStorage.removeItem("userId");
  }
}
