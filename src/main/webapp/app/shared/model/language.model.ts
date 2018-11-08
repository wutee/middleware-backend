export interface ILanguage {
    id?: number;
    phrase?: string;
}

export class Language implements ILanguage {
    constructor(public id?: number, public phrase?: string) {}
}
