import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-adduser',
  templateUrl: './adduser.component.html',
  styleUrls: ['./adduser.component.css']
})
export class AdduserComponent implements OnInit {

  constructor(private authService:AuthService) { }

  form: any = {
    role: '',
    firstname:'',
    lastname:'',
    email: '',
    mobile:'',
    password: '',
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  ngOnInit(): void {
  }
  
  onSubmit(){
    const { firstname,lastname, email, password,mobile } = this.form;
    const roles:Array<string>=[this.form.role];
    this.authService.createUser(roles,firstname,lastname, email, password,mobile).subscribe(
      data=>{
        this.isSuccessful=true;
        console.log(data);
        
      },
      err=>{
        this.errorMessage=err.error.message;
        console.log(this.errorMessage);
        
      }
    )
  }

}
