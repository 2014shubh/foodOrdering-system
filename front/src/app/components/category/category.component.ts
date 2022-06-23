import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { CartService } from 'src/app/service/cart.service';
import { CategoryService } from 'src/app/service/category.service';
import { NavComponent } from '../shared/nav/nav.component';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  categories:any=[];
  index=0;

  constructor(private cartService:CartService,private categoryService:CategoryService) { }
  productList:Product[]=[];


  ngOnInit(): void {
    this.getCategory();
  }

  addtocart(product: any){
    this.cartService.addtoCart(product);
  
  }
  getCategory(){
    this.categoryService.getCategoryList().subscribe(
      data=>{
        this.categories=data;
      },
      error=>{
        console.log(error);
      }
    )
  }

  getProducts(i:number){
    this.index=i;
    console.log(this.index);
    
    this.categoryService.getCategoryList().subscribe(
      data=>{
        this.categories=data;
        this.productList=this.categories[this.index].products;
      },
      error=>{
        console.log(error);
        
      }
    )
  }
}
