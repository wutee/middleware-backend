export interface IIngredient {
    id?: number;
    nameSlug?: string;
    photoLocation?: string;
    ingredientDescription?: string;
}

export class Ingredient implements IIngredient {
    constructor(public id?: number, public nameSlug?: string, public photoLocation?: string, public ingredientDescription?: string) {}
}
