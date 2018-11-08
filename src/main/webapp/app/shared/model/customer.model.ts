export interface ICustomer {
    id?: number;
    name?: string;
    mail?: string;
    role?: number;
    address?: string;
    city?: string;
    phone?: string;
    loyaltyPoints?: number;
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public name?: string,
        public mail?: string,
        public role?: number,
        public address?: string,
        public city?: string,
        public phone?: string,
        public loyaltyPoints?: number
    ) {}
}
