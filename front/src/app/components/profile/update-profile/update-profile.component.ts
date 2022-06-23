import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/service/tokenstorage.service';
import { UserServiceService } from 'src/app/service/user-service.service';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {

  form: any = {
    firstname:'',
    lastname:'',
    email: '',
    mobile:''
  };
  
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  userId:string;

  constructor(private userService:UserServiceService,private tokenStorage:TokenStorageService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    const { firstname,lastname,mobile } = this.form;
    this.userId=this.tokenStorage.getUser().userId;
    this.userService.updateUser(this.userId,firstname,lastname, mobile).subscribe(
      data=>{
        console.log(data);
        this.isSuccessful=true;
        this.isSignUpFailed=false;
      },
      err=>{
        this.errorMessage=err.error.message;
        this.isSignUpFailed=true;
        console.log(err);
        
      }
    )
  }

}
