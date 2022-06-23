import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthGuard } from './auth.guard';
import { TokenStorageService } from './service/tokenstorage.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  roles:string[]=[];
  constructor(private authGuard:AuthGuard,private tokenStorage:TokenStorageService,private router:Router){}

  canActivate():boolean{
    this.roles=this.tokenStorage.getUser().roles;
    if(this.roles==undefined || (!this.roles.includes('ROLE_ADMIN'))){
      this.router.navigate(['/unauthorised'])
      return false;
    }else{
      
      return true;
    }
  }
}