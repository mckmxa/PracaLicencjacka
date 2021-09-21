import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { WishListComponent} from './wishlist/wishlist.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { BrowseComponent } from './browse/browse.component';
import { AuthGuard } from './guard/auth.guard';
import { RoleGuard } from './guard/role.guard';
import { FeaturesComponent } from './features/features.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'wishlist', component: WishListComponent, canActivate: [AuthGuard] },
  { path: 'admin', component: AdminPanelComponent, canActivate: [AuthGuard, RoleGuard] },
  { path: 'browse', component: BrowseComponent, canActivate: [AuthGuard] },
  { path: 'features', component: FeaturesComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }