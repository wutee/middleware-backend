export interface IDeliveryMan {
    id?: number;
    name?: string;
    surname?: string;
}

export class DeliveryMan implements IDeliveryMan {
    constructor(public id?: number, public name?: string, public surname?: string) {}
}
