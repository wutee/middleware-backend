import { Moment } from 'moment';

export interface IIngredientOrder {
    id?: number;
    date?: Moment;
    comment?: string;
}

export class IngredientOrder implements IIngredientOrder {
    constructor(public id?: number, public date?: Moment, public comment?: string) {}
}
