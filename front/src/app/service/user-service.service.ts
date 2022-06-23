import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:9003/auth/';


@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private http: HttpClient) { }



  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin/', { responseType: 'text' });
  }

  getDeliveryBoard(): Observable<any> {
    return this.http.get(API_URL + 'delivery', { responseType: 'text' });
  }

  changePassword(token:string,password:string): Observable<any> {
    return this.http.post(API_URL + 'pass/reset-password',{
      token,
      password
    });
  }

  sendCode(email:string):Observable<any>{
    return this.http.post(API_URL + 'user/verifyEmail', {
      email
    });
  }

  getUser(userId:string):Observable<any>{
    return this.http.get(API_URL+'user/getUser/'+userId);
  }

  updateUser(userId:string,firstname:string,lastname:string,mobile:string ){
    return this.http.put(API_URL+'user/update/'+userId,{
      firstname,lastname,mobile 
    });
  }

  getNegativePoints(cid:number){
    return this.http.get(API_URL+'user/getPoints'+cid);
  }

}

