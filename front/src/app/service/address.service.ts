import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const API_URL = 'http://localhost:9003/auth/';
@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private http:HttpClient) { }

  getAddress(cid:number):Observable<any>{
    return this.http.get(API_URL+'user/getAddress/'+cid);
  }

  addAddress(cid:number,houseNum:number,street:string,area:string,city:string,pincode:string):Observable<any>{
    return this.http.post(API_URL+'user/addAddress/'+cid,{
      houseNum,
      street,
      area,
      city,
      pincode
    });
  }

  deleteAddress(id:number){
    return this.http.delete(API_URL+'user/deleteAddress/'+id);
  }
}
