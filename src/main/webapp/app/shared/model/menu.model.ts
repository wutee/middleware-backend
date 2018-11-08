import { IRestaurant } from 'app/shared/model//restaurant.model';
import { IFoodInMenu } from 'app/shared/model//food-in-menu.model';

export interface IMenu {
    id?: number;
    nameSlug?: string;
    restaurant?: IRestaurant;
    foodLists?: IFoodInMenu[];
}

export class Menu implements IMenu {
    constructor(public id?: number, public nameSlug?: string, public restaurant?: IRestaurant, public foodLists?: IFoodInMenu[]) {}
}
