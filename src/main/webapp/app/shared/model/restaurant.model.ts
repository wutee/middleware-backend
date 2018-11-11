import { IMenu } from 'app/shared/model//menu.model';
import { IFoodOrder } from 'app/shared/model//food-order.model';
import { IRestaurantWorker } from 'app/shared/model//restaurant-worker.model';

export interface IRestaurant {
    id?: number;
    nameSlug?: string;
    address?: string;
    ownerId?: string;
    menus?: IMenu[];
    orders?: IFoodOrder[];
    employees?: IRestaurantWorker[];
}

export class Restaurant implements IRestaurant {
    constructor(
        public id?: number,
        public nameSlug?: string,
        public address?: string,
        public ownerId?: string,
        public menus?: IMenu[],
        public orders?: IFoodOrder[],
        public employees?: IRestaurantWorker[]
    ) {}
}
