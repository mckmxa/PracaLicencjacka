import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../services/token-storage.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  content?: string;
  isLoggedIn = false;

  constructor(private tokenStorageService: TokenStorageService, private router:Router) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    
    
    
  }

  goRegister(): void {
    this.router.navigate(['/register']);
  }

  goBrowse(): void {
    this.router.navigate(['/browse']);
  }
}