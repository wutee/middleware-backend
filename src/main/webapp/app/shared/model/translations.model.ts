export interface ITranslations {
    id?: number;
    translation?: string;
}

export class Translations implements ITranslations {
    constructor(public id?: number, public translation?: string) {}
}
