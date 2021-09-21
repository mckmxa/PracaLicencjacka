import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';
import { TokenStorageService } from '../services/token-storage.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {
    username: null,
    email: null,
    password: null
  };
  aFormGroup: FormGroup;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  siteKey: string;
  captchaMessage = '';
  

  constructor(private authService: AuthService, private tokenStorageService: TokenStorageService, private router: Router, private formBuilder: FormBuilder, private notifyService:NotificationService) {
     this.siteKey='6Lec6hwcAAAAAF_YtPlYiA_NNutIVqkwPM0wtvo6';
     if (this.tokenStorageService.getToken()) {
      this.router.navigate(['/']);
  }
     }

  ngOnInit(): void {
    this.aFormGroup = this.formBuilder.group({
      recaptcha: ['', Validators.required]
    });
  }

  onSubmit(): void {
    
    const { username, email, password} = this.form;
    


    if (this.aFormGroup.invalid) {
      this.errorMessage = "Captcha verification required"
      this.isSignUpFailed = true;
      return;
  }

    this.authService.register(username, email, password).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.router.navigate(['/login']);
        this.showToasterSuccessRegister();
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        this.showToasterErrorRegister();
      }
    );
  }



  reloadPage(): void {
    window.location.reload();
  }

  // CUSTOMIZING ALERTS FOR THIS COMPONENT //
    // CUSTOMIZING ALERTS FOR THIS COMPONENT //
    showToasterSuccessRegister(){
      this.notifyService.showSuccess("Success! You can log in now!", "dealalert.com")
    }
    
    showToasterErrorRegister(){
      this.notifyService.showError("Username is already in use", "dealalert.com")
    }
}