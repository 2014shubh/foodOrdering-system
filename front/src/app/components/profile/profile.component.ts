import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/service/tokenstorage.service';
import { UserServiceService } from 'src/app/service/user-service.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private userService:UserServiceService,private tokenStorage:TokenStorageService) { }
  email:string;
  firstname:string;
  lastname:string;
  mobile:string;
  negativePoints:number;
  userId:string;


  ngOnInit(): void {
    this.userId=this.tokenStorage.getUser().userId;
    this.userService.getUser(this.userId).subscribe(
      data=>{
        this.email=data.email;
        this.firstname=data.firstname;
        this.lastname=data.lastname;
        this.mobile=data.mobile;
        this.negativePoints=data.negativePoints;
        console.log(data);
        
      },
      err=>{
        console.log(err);
        
      }
    )
  }

}
