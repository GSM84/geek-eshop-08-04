import {Category} from "./category";
import {Brand} from "./brand";

export class Product {

  constructor(public id: number,
              public title: string,
              public price: number,
              public category: Category,
              public brand: Brand,
              public pictures: number[]
              ) {
  }
}
