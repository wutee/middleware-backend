import { IFood } from 'app/shared/model//food.model';
import { ILanguage } from 'app/shared/model//language.model';

export interface ITranslation {
    id?: number;
    translation?: string;
    food?: IFood;
    language?: ILanguage;
}

export class Translation implements ITranslation {
    constructor(public id?: number, public translation?: string, public food?: IFood, public language?: ILanguage) {}
}
