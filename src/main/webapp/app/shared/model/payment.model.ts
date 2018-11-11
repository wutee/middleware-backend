import { IPaymentMethod } from 'app/shared/model//payment-method.model';

export interface IPayment {
    id?: number;
    status?: number;
    methods?: IPaymentMethod[];
}

export class Payment implements IPayment {
    constructor(public id?: number, public status?: number, public methods?: IPaymentMethod[]) {}
}
