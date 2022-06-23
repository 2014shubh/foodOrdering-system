import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/models/address';
import { AddressService } from 'src/app/service/address.service';
import { TokenStorageService } from 'src/app/service/tokenstorage.service';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {

  constructor(private tokenStorage:TokenStorageService,private addressService:AddressService) { }

  addresses:any[];
  address=new Address();

  cid:number;
  ngOnInit(): void {
    this.cid=this.tokenStorage.getUser().cid;
    this.addressService.getAddress(this.cid).subscribe(
      data=>{
        this.addresses=data;
        console.log(this.addresses);
        
      },
      err=>{
        console.log(err);
        
      }
    )
  }

  deleteAddress(i:number){

    this.addressService.deleteAddress(this.addresses[i].addressId).subscribe(
      data=>{
        console.log(data);
        window.location.reload();
        
      },
      err=>{
        console.log(err);
        
      }
    )
  }

}
