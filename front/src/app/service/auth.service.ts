import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from './tokenstorage.service';


const AUTH_API = 'http://localhost:9003/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient,private tokenStorage:TokenStorageService) { }


  loggedIn(){
    return !!this.tokenStorage.getToken();
  }
  login(email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      email,
      password
    },httpOptions);
  }

  register(firstname: string,lastname: string, email: string, password: string,mobile:string): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      firstname,
      lastname,
      email,
      password,
      mobile
    }, httpOptions);
  }

  createUser(role:string[],firstname: string,lastname: string, email: string, password: string,mobile:string): Observable<any> {
    return this.http.post(AUTH_API+'admin/addUser',{
      role,
      firstname,
      lastname,
      email,
      password,
      mobile
    })
  }
}
