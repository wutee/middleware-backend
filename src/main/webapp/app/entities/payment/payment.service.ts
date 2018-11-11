import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPayment } from 'app/shared/model/payment.model';

type EntityResponseType = HttpResponse<IPayment>;
type EntityArrayResponseType = HttpResponse<IPayment[]>;

@Injectable({ providedIn: 'root' })
export class PaymentService {
    public resourceUrl = SERVER_API_URL + 'api/payments';

    constructor(private http: HttpClient) {}

    create(payment: IPayment): Observable<EntityResponseType> {
        return this.http.post<IPayment>(this.resourceUrl, payment, { observe: 'response' });
    }

    update(payment: IPayment): Observable<EntityResponseType> {
        return this.http.put<IPayment>(this.resourceUrl, payment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPayment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPayment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
