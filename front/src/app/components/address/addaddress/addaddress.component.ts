import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AddressService } from 'src/app/service/address.service';
import { TokenStorageService } from 'src/app/service/tokenstorage.service';

@Component({
  selector: 'app-addaddress',
  templateUrl: './addaddress.component.html',
  styleUrls: ['./addaddress.component.css']
})
export class AddaddressComponent implements OnInit {

  form: any = {
    street:'',
    area:'',
    city: '',
    pincode:''
  };
  houseNum:number;
  cid:number;
  isSuccessful=false;
  isSignUpFailed=false;
  errorMessage='';

  constructor(private addressService:AddressService,private tokenStorage:TokenStorageService,
    private router:Router) { }

  ngOnInit(): void {
    this.cid=this.tokenStorage.getUser().cid;
  }

  onSubmit(){
    const{street,area,city,pincode}=this.form;

    this.addressService.addAddress(this.cid,this.houseNum,street,area,city,pincode).subscribe(
      data=>{
        console.log(data);
        this.isSuccessful=true;
        this.isSignUpFailed=false;
        this.router.navigate(['/address']);
      },
      err=>{
        this.errorMessage=err.error.message;
        console.log(this.errorMessage);
        this.isSignUpFailed=true;
      }
    )
  }

 

}
