import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from 'src/app/models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseURL="http://localhost:9003/product/all";


  
  //products:Product[]=[
    // new Product(1,'Product 1','this is product1',100,''),
    // new Product(2,'Product 2','this is product2',200,''),
    // new Product(3,'Product 3','this is product3',300,''),
    // new Product(4,'Product 4','this is product4',400,''),
    // new Product(5,'Product 5','this is product5',600,''),

 // ]

  constructor(private httpClient: HttpClient) { }
  // getProducts():Product[]
  // {
  //   // TOdo:populate products from api and return an Observable
  //   return this.products
  // }
  getProductsList(page:number):Observable<any>{
    return this.httpClient.get<any>(`${this.baseURL}`+'?page='+page);
  }

  createProduct(product: Product):Observable<any>{
    return this.httpClient.post(`${this.baseURL}`,product);
  }

}
