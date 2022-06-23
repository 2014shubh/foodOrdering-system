import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { CartService } from 'src/app/service/cart.service';
import { CategoryService } from 'src/app/service/category.service';
import { TokenStorageService } from 'src/app/service/tokenstorage.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})

export class NavComponent implements OnInit {


  categories:any=[];
  index=0;

  private roles: string[] = [];
  isLoggedIn = false;
  firstname?: string;
  showAdminBoard = false;
  showModeratorBoard = false;
  showDeliveryBoard=false;
  flag=0;
  title: any;
  public searchTerm: string= '';
  constructor(private cartService:CartService,private tokenStorageService: TokenStorageService,
    private categoryService:CategoryService,private router:Router) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');
      this.showDeliveryBoard = this.roles.includes('ROLE_DELIVERY');

      this.firstname = user.firstname;
    }
  }

  search(event:any){
    this.searchTerm = (event.target as HTMLInputElement).value;
    console.log(this.searchTerm);
    this.cartService.search.next(this.searchTerm);
  }
  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
export type Credentials = { index:number };
