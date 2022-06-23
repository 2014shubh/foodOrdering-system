import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard} from './auth.guard';
import { AddaddressComponent } from './components/address/addaddress/addaddress.component';
import { AddressComponent } from './components/address/address.component';
import { AdduserComponent } from './components/admin/adduser/adduser.component';
import { AdminComponent } from './components/admin/admin.component';
import { CategoryComponent } from './components/category/category.component';
import { ForgotpasswordComponent } from './components/forgotpassword/forgotpassword.component';
import { CartComponent } from './components/grocery-cart/cart/cart.component';
import { ProductListComponent } from './components/grocery-cart/product-list/product-list.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { UpdateProfileComponent } from './components/profile/update-profile/update-profile.component';
import { RegisterComponent } from './components/register/register.component';
import { GroceryCartComponent } from './components/shared/grocery-cart/grocery-cart.component';
import { UnauthorisedComponent } from './components/unauthorised/unauthorised.component';
import { RoleGuard } from './role.guard';
import { ForgotpasswordService } from './service/forgotpassword.service';


const routes: Routes = [
  
  {
     path: 'home', component: HomeComponent
   },
   {
    path:'',redirectTo:'home',
    pathMatch:'full',
  },
  //  {
  //    path:'', redirectTo:'products',pathMatch:'full'
  //  },
  {
    path:'cart',component: CartComponent,
    pathMatch:'full',
    canActivate:[AuthGuard]
  },
   {
    path:'login',component: LoginComponent
  },
  {
    path:'register',component: RegisterComponent
  },
  {
    path:'address',component: AddressComponent,
    pathMatch:'full',
    canActivate:[AuthGuard]
  },
  {
    path:'admin',component: AdminComponent,
    pathMatch:'full',
    canActivate:[RoleGuard]
  },
  {
    path:'unauthorised',component:UnauthorisedComponent,
  },
  {
    path:'forgotpassword',component:ForgotpasswordComponent,
  },
  {
    path:'category',component:CategoryComponent,
  },
  {
    path:'adduser',component:AdduserComponent,
    pathMatch:'full',
    canActivate:[RoleGuard]
  },
  {
    path:'addaddress',component:AddaddressComponent,
    pathMatch:'full',
    canActivate:[AuthGuard]
  },
  {
    path:'profile',component:ProfileComponent,
    pathMatch:'full',
    canActivate:[AuthGuard]

  },
  {
    path:'updateprofile',component:UpdateProfileComponent,
    pathMatch:'full',
    canActivate:[AuthGuard]

  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
