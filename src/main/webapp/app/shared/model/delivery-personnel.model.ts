import { IFoodOrder } from 'app/shared/model//food-order.model';

export interface IDeliveryPersonnel {
    id?: number;
    name?: string;
    surname?: string;
    orders?: IFoodOrder[];
}

export class DeliveryPersonnel implements IDeliveryPersonnel {
    constructor(public id?: number, public name?: string, public surname?: string, public orders?: IFoodOrder[]) {}
}
