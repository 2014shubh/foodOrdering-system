import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:9003/auth/pass/';

@Injectable({
  providedIn: 'root'
})
export class ForgotpasswordService {

  constructor(private http: HttpClient) { }

  sendCode(email:string):Observable<any>{
    return this.http.post(API_URL + 'forgot-password', {
      email
    });
  }
}

