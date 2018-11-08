import { Moment } from 'moment';
import { IFoodInOrder } from 'app/shared/model//food-in-order.model';

export interface ICustomerOrder {
    id?: number;
    date?: Moment;
    lastUpdatedDate?: Moment;
    status?: number;
    price?: number;
    userOpinion?: string;
    userComment?: string;
    deliveryManComment?: string;
    loyaltyPoints?: number;
    foodLists?: IFoodInOrder[];
}

export class CustomerOrder implements ICustomerOrder {
    constructor(
        public id?: number,
        public date?: Moment,
        public lastUpdatedDate?: Moment,
        public status?: number,
        public price?: number,
        public userOpinion?: string,
        public userComment?: string,
        public deliveryManComment?: string,
        public loyaltyPoints?: number,
        public foodLists?: IFoodInOrder[]
    ) {}
}
