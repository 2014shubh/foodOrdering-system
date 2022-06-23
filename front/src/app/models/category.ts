import { Product } from "./product";

export class Category {
    id:number;
    categoryName:string;
    description:string;
    products:Product[]=[];
}
