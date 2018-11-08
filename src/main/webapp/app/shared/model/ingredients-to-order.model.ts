export interface IIngredientsToOrder {
    id?: number;
    quantity?: number;
}

export class IngredientsToOrder implements IIngredientsToOrder {
    constructor(public id?: number, public quantity?: number) {}
}
