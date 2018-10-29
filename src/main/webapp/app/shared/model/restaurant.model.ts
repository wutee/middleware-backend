export interface IRestaurant {
    id?: number;
    name?: string;
    address?: string;
}

export class Restaurant implements IRestaurant {
    constructor(public id?: number, public name?: string, public address?: string) {}
}
