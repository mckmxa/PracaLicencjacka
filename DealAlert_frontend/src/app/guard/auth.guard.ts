import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';

import { TokenStorageService } from '../services/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private tokenStorageService: TokenStorageService
) {}
canActivate(): boolean {
  if (!this.tokenStorageService.getToken()) {
    this.router.navigate(['login']);
    return false;
  }

  return true;
}

checkUserLogin(route: ActivatedRouteSnapshot, url: any): boolean {
  if (!this.tokenStorageService.getToken()) {
    const user = this.tokenStorageService.getUser();
    const roles = user.roles;
    if (roles.includes('ROLE_ADMIN')) {
      this.router.navigate(['/home']);
      return false;
    }
    return true;
  }

  this.router.navigate(['/home']);
  return false;
}
  
}
