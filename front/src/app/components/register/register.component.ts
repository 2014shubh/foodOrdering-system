import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/service/auth.service';
import { UserServiceService } from 'src/app/service/user-service.service';
import swal from 'sweetalert';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form:any={
    cpassword:null
  }

  user=new User();
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  emailResponse='';
  emailRes=false;
  emailEr=false;
  emailErr='';
  emailToken='';
  tokenRes='';
  tokenR=false;
  token='';
  tokenErr=false;
 

  constructor(private authService: AuthService,private userService:UserServiceService,private router:Router) { }


  ngOnInit(): void {
    this.emailRes=false;
  }

  onSubmit(): void {
    const { firstname,lastname, email, password,mobile } = this.user;

    this.authService.register(firstname,lastname,email, password,mobile).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        swal("Good job!", "You have registered successfully!", "success");
        this.router.navigate(['/login']);
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

  sendMail(){
   let email=this.user.email;
   console.log(this.user.email);
   
    this.userService.sendCode(email).subscribe(
      data=>{
        this.emailResponse=data.message;
        console.log(this.emailResponse);
        this.emailRes=true;
        this.emailToken=data.token;
        console.log(this.emailToken);
        
      },
      err=>{
        this.emailErr=err.error.message;
        this.emailEr=true;
      }
    )
  }

  validateToken(){
    if(this.token===this.emailToken){
      console.log("true");
      this.emailRes=false;
      this.tokenRes="Email Verified";
      this.tokenR=true;
    }
    else{
      this.tokenErr=true;
      this.tokenRes="Invalid token";
    }
  }
}


