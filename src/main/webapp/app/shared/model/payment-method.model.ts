export interface IPaymentMethod {
    id?: number;
    paymentName?: string;
}

export class PaymentMethod implements IPaymentMethod {
    constructor(public id?: number, public paymentName?: string) {}
}
