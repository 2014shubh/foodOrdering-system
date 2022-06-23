import { Component, OnInit } from '@angular/core';
import { ForgotpasswordService } from 'src/app/service/forgotpassword.service';
import { UserServiceService } from 'src/app/service/user-service.service';

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.css']
})
export class ForgotpasswordComponent implements OnInit {

  token='';
  password='';
  sent=false;
  error=false;
  formError=false;
  email='';
  successChange=false;
  remForm=true;
  message=''
  errorMessage='';
  constructor(private forgotPassword:ForgotpasswordService,private userService:UserServiceService) { }

  ngOnInit(): void {
    this.sent=false;
    this.error=false;
  }
  form: any = {
    cpassword:null
  }
  sendMail(){
    this.forgotPassword.sendCode(this.email).subscribe(
      data=>{
        this.message=data.message;
        console.log(this.message);
        this.sent=true;
        this.successChange=true;
      },
      err => {
        this.errorMessage = err.error.message;
        this.error=true;
      }
    )
  }
  onSubmit():void{
    this.userService.changePassword(this.token,this.password).subscribe(
      data=>{
        this.message=data.message;
        console.log(this.message);
        this.successChange=false;
        this.remForm=false;
      },
      err=>{
        this.errorMessage=err.error.message;
        this.formError=true;
      }
    )
  }

}
  