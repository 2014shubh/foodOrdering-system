import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseURL="http://localhost:9003/category/";

  constructor(private httpClient:HttpClient) { }

  getCategoryList():Observable<any>{
    return this.httpClient.get<any>(`${this.baseURL}`+"all");
  }
}
