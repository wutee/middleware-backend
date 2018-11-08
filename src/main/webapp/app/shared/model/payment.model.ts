export interface IPayment {
    id?: number;
    status?: number;
}

export class Payment implements IPayment {
    constructor(public id?: number, public status?: number) {}
}
