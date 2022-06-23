
import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { CartService } from 'src/app/service/cart.service';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

 private page:number=0;
  products: Product[];
  searchKey: string = "";
  totalPage:number;
  currentPage:number;
 
  constructor(private productService:ProductService,private cartService: CartService) { }

  ngOnInit(): void {

    this.getProducts();
    
    
  }

  private getProducts(){
  this.productService.getProductsList(this.page).subscribe(data => {
    console.log(data);
    
    this.products=(data.productList);
    this.totalPage=data.totalPages;
    this.currentPage=data.currentPage;

    this.products.forEach((a:any) => {
      Object.assign(a,{quantity:1,total:a.price})
     });
  });
  this.cartService.search.subscribe((val:any) => {
    this.searchKey=val;

  })
}
addtocart(product: any){
  this.cartService.addtoCart(product);

}

counter(i: number) {
  return new Array(i);
}

getPage(i:number){
  this.page=i;
  this.getProducts();
}

}
