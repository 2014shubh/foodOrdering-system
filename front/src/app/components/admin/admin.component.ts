import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/service/user-service.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  content:string

  public userList: any =[];


  constructor(private userService:UserServiceService) { }

  ngOnInit(): void {
    this.userService.getAdminBoard().subscribe(
      data => {
        this.userList=JSON.parse(data);
        console.log(this.userList);
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
    }
}
