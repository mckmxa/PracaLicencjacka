import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { NgxCaptchaModule } from 'ngx-captcha';
import { NgxChartsModule }from '@swimlane/ngx-charts';


import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatInputModule} from '@angular/material/input';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { WishListComponent } from './wishlist/wishlist.component';

import { authInterceptorProviders } from './helpers/auth.interceptor';
import { BrowseComponent } from './browse/browse.component';
import { FooterComponent } from './footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import {MatExpansionModule} from '@angular/material/expansion';
import { FeaturesComponent } from './features/features.component';





@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    AdminPanelComponent,
    WishListComponent,
    BrowseComponent,
    FooterComponent,
    FeaturesComponent,
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgxPaginationModule,
    MatAutocompleteModule,
    MatInputModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatSlideToggleModule,
    NgxCaptchaModule,
    NgxChartsModule,
    MatExpansionModule,
    ToastrModule.forRoot(),
    
  ],
  providers: [
    authInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
