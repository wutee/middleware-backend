export interface IRestaurantWorker {
    id?: number;
    name?: string;
    surname?: string;
    workingStatus?: number;
}

export class RestaurantWorker implements IRestaurantWorker {
    constructor(public id?: number, public name?: string, public surname?: string, public workingStatus?: number) {}
}
