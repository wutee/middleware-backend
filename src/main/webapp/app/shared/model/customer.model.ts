import { IFoodOrder } from 'app/shared/model//food-order.model';

export interface ICustomer {
    id?: number;
    name?: string;
    mail?: string;
    role?: number;
    address?: string;
    city?: string;
    phone?: string;
    loyaltyPoints?: number;
    orders?: IFoodOrder[];
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public name?: string,
        public mail?: string,
        public role?: number,
        public address?: string,
        public city?: string,
        public phone?: string,
        public loyaltyPoints?: number,
        public orders?: IFoodOrder[]
    ) {}
}
