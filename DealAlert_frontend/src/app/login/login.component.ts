import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';
import { TokenStorageService } from '../services/token-storage.service';

 

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  username = '';

  constructor(private authService: AuthService, private tokenStorageService: TokenStorageService, private router: Router, private notifyService : NotificationService) {
    if (this.tokenStorageService.getToken()) {
      this.goBrowse();
  }
   }

  ngOnInit(): void {
    if (this.tokenStorageService.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorageService.getUser().roles;
      this.username = this.tokenStorageService.getUser().username;
    }
  }

  onSubmit(): void {
    const { username, password } = this.form;

    this.authService.login(username, password).subscribe(
      data => {
        this.tokenStorageService.saveToken(data.accessToken);
        this.tokenStorageService.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorageService.getUser().roles;
        this.username = this.tokenStorageService.getUser().username;
        this.goBrowse();
        this.reloadPage();

      },
      err => {
        this.showToasterErrorLogin();
        this.isLoginFailed = true;
      }
    );
  }
  
  reloadPage(): void {
    window.location.reload();
  }

  goHome(): void {
    this.router.navigate(['/home']);
  }

  goBrowse(): void {
    this.router.navigate(['/browse']);
  }

  // CUSTOMIZING ALERTS FOR THIS COMPONENT //
  showToasterSuccessLogin(){
    this.notifyService.showSuccess("Successfully logged in!", "dealalert.com")
  }
  
  showToasterErrorLogin(){
    this.notifyService.showError("Bad credentials! Try again.", "dealalert.com")
  }
}