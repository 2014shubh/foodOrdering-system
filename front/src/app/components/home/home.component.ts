import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/service/tokenstorage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

 
  constructor( private tokenStorage:TokenStorageService) { }
  isLoggedIn = false;
   username:string;
  ngOnInit(): void {
    if(this.tokenStorage.getToken){
      this.isLoggedIn = !!this.tokenStorage.getToken();
      
    if (this.isLoggedIn) {
      const user = this.tokenStorage.getUser();
       this.username=user.firstname;
    }
  }

}
}

