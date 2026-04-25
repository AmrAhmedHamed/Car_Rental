import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';
import {LoginSignUpGuardGuard} from "./login-sign-up-guard.guard";


describe('LoginSignUpGuardGuard', () => {
  let guard: LoginSignUpGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(LoginSignUpGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
