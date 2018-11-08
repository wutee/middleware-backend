export interface IDeliveries {
    id?: number;
}

export class Deliveries implements IDeliveries {
    constructor(public id?: number) {}
}
