import { Role } from "./role";

export class UserBoard {

    userId:string;
    email: string;
    firstname :string; 
    lastname :string;
    mobile:string;
    UserBoard:Role[]=[];

    constructor() {}
}
