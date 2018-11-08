import { IFoodInOrder } from 'app/shared/model//food-in-order.model';
import { IFoodInMenu } from 'app/shared/model//food-in-menu.model';

export interface IFood {
    id?: number;
    nameSlug?: string;
    foodDescription?: string;
    calories?: number;
    isSpicy?: boolean;
    isVegetarian?: boolean;
    isGlutenFree?: boolean;
    photoLocation?: string;
    order?: IFoodInOrder;
    menuList?: IFoodInMenu;
}

export class Food implements IFood {
    constructor(
        public id?: number,
        public nameSlug?: string,
        public foodDescription?: string,
        public calories?: number,
        public isSpicy?: boolean,
        public isVegetarian?: boolean,
        public isGlutenFree?: boolean,
        public photoLocation?: string,
        public order?: IFoodInOrder,
        public menuList?: IFoodInMenu
    ) {
        this.isSpicy = this.isSpicy || false;
        this.isVegetarian = this.isVegetarian || false;
        this.isGlutenFree = this.isGlutenFree || false;
    }
}
