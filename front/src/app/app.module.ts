import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { NavComponent } from './components/shared/nav/nav.component';
import { GroceryCartComponent } from './components/shared/grocery-cart/grocery-cart.component';
import { ProductListComponent } from './components/grocery-cart/product-list/product-list.component';
import { CartComponent } from './components/grocery-cart/cart/cart.component';
import { FiltersComponent } from './components/grocery-cart/filters/filters.component';
import { CartItemComponent } from './components/grocery-cart/cart/cart-item/cart-item.component';
import { ProductItemComponent } from './components/grocery-cart/product-list/product-item/product-item.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { AdminComponent } from './components/admin/admin.component';
import { DeliverypartnerComponent } from './components/deliverypartner/deliverypartner.component';
import { InventoryComponent } from './components/inventory/inventory.component';
import { FilterPipe } from './filter.pipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthInterceptor } from './_helpers/auth.interceptor';
import { NgxPaginationModule } from 'ngx-pagination';
import { AuthGuard} from './auth.guard';
import { UnauthorisedComponent } from './components/unauthorised/unauthorised.component';
import { RoleGuard } from './role.guard';
import { ForgotpasswordComponent } from './components/forgotpassword/forgotpassword.component';
import { AddressComponent } from './components/address/address.component';
import { CategoryComponent } from './components/category/category.component';
import { AdduserComponent } from './components/admin/adduser/adduser.component';
import { AddaddressComponent } from './components/address/addaddress/addaddress.component';
import { ProfileComponent } from './components/profile/profile.component';
import { UpdateProfileComponent } from './components/profile/update-profile/update-profile.component';
import { PaymentComponent } from './components/payment/payment.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NavComponent,
    GroceryCartComponent,
    ProductListComponent,
    CartComponent,
    FiltersComponent,
    CartItemComponent,
    ProductItemComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    AdminComponent,
    DeliverypartnerComponent,
    InventoryComponent,
    FilterPipe,
    UnauthorisedComponent,
    ForgotpasswordComponent,
    AddressComponent,
    CategoryComponent,
    AdduserComponent,
    AddaddressComponent,
    ProfileComponent,
    UpdateProfileComponent,
    PaymentComponent,
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxPaginationModule,
    ReactiveFormsModule
    
  ],
  providers: [{provide:HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true},AuthGuard,RoleGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
