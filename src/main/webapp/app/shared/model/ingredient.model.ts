import { IIngredientOrder } from 'app/shared/model//ingredient-order.model';

export interface IIngredient {
    id?: number;
    nameSlug?: string;
    price?: number;
    photoLocation?: string;
    ingredientDescription?: string;
    orders?: IIngredientOrder[];
}

export class Ingredient implements IIngredient {
    constructor(
        public id?: number,
        public nameSlug?: string,
        public price?: number,
        public photoLocation?: string,
        public ingredientDescription?: string,
        public orders?: IIngredientOrder[]
    ) {}
}
