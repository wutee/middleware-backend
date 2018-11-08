import { IMenu } from 'app/shared/model//menu.model';
import { IRestaurantWorker } from 'app/shared/model//restaurant-worker.model';

export interface IRestaurant {
    id?: number;
    nameSlug?: string;
    address?: string;
    ownerId?: string;
    menu?: IMenu;
    employees?: IRestaurantWorker[];
}

export class Restaurant implements IRestaurant {
    constructor(
        public id?: number,
        public nameSlug?: string,
        public address?: string,
        public ownerId?: string,
        public menu?: IMenu,
        public employees?: IRestaurantWorker[]
    ) {}
}
