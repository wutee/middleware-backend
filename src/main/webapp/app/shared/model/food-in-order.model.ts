import { ICustomerOrder } from 'app/shared/model//customer-order.model';
import { IFood } from 'app/shared/model//food.model';

export interface IFoodInOrder {
    id?: number;
    orderList?: ICustomerOrder;
    foodLists?: IFood[];
}

export class FoodInOrder implements IFoodInOrder {
    constructor(public id?: number, public orderList?: ICustomerOrder, public foodLists?: IFood[]) {}
}
