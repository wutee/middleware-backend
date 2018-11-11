import { Moment } from 'moment';
import { IIngredient } from 'app/shared/model//ingredient.model';

export interface IIngredientOrder {
    id?: number;
    date?: Moment;
    totalPrice?: number;
    comment?: string;
    ingredientLists?: IIngredient[];
}

export class IngredientOrder implements IIngredientOrder {
    constructor(
        public id?: number,
        public date?: Moment,
        public totalPrice?: number,
        public comment?: string,
        public ingredientLists?: IIngredient[]
    ) {}
}
