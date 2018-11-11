import { ITranslation } from 'app/shared/model//translation.model';

export interface ILanguage {
    id?: number;
    nameSlug?: string;
    translations?: ITranslation[];
}

export class Language implements ILanguage {
    constructor(public id?: number, public nameSlug?: string, public translations?: ITranslation[]) {}
}
