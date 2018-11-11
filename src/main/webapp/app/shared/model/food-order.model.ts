import { Moment } from 'moment';
import { IRestaurant } from 'app/shared/model//restaurant.model';
import { ICustomer } from 'app/shared/model//customer.model';
import { IDeliveryPersonnel } from 'app/shared/model//delivery-personnel.model';
import { IFood } from 'app/shared/model//food.model';

export interface IFoodOrder {
    id?: number;
    date?: Moment;
    lastUpdatedDate?: Moment;
    status?: number;
    price?: number;
    userOpinion?: string;
    userComment?: string;
    deliveryManComment?: string;
    loyaltyPoints?: number;
    restaurant?: IRestaurant;
    orderee?: ICustomer;
    delivery?: IDeliveryPersonnel;
    foodItems?: IFood[];
}

export class FoodOrder implements IFoodOrder {
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
        public restaurant?: IRestaurant,
        public orderee?: ICustomer,
        public delivery?: IDeliveryPersonnel,
        public foodItems?: IFood[]
    ) {}
}
