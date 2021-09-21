import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { TokenStorageService } from '../services/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  private roles: string[] = [];
  constructor(
    private router: Router,
    private tokenStorageService: TokenStorageService
) {}

canActivate(): boolean {
  if (this.tokenStorageService.getToken()) {
    const user = this.tokenStorageService.getUser();
    this.roles = user.roles;
    if (!this.roles.includes('ROLE_ADMIN')) {
      this.router.navigate(['/home']);
      return false;
    }
    return true;
  }

  this.router.navigate(['/home']);
  return false;
}
  
}
