import { IRestaurant } from 'app/shared/model//restaurant.model';

export interface IRestaurantWorker {
    id?: number;
    workerName?: string;
    workerSurname?: string;
    workingStatus?: number;
    employers?: IRestaurant[];
}

export class RestaurantWorker implements IRestaurantWorker {
    constructor(
        public id?: number,
        public workerName?: string,
        public workerSurname?: string,
        public workingStatus?: number,
        public employers?: IRestaurant[]
    ) {}
}
