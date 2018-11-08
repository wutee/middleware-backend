import { IMenu } from 'app/shared/model//menu.model';
import { IFood } from 'app/shared/model//food.model';

export interface IFoodInMenu {
    id?: number;
    price?: number;
    menu?: IMenu;
    foodLists?: IFood[];
}

export class FoodInMenu implements IFoodInMenu {
    constructor(public id?: number, public price?: number, public menu?: IMenu, public foodLists?: IFood[]) {}
}
