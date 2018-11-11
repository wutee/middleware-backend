import { IPayment } from 'app/shared/model//payment.model';

export interface IPaymentMethod {
    id?: number;
    paymentName?: string;
    payment?: IPayment;
}

export class PaymentMethod implements IPaymentMethod {
    constructor(public id?: number, public paymentName?: string, public payment?: IPayment) {}
}
