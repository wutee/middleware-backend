import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeliveryMan } from 'app/shared/model/delivery-man.model';

type EntityResponseType = HttpResponse<IDeliveryMan>;
type EntityArrayResponseType = HttpResponse<IDeliveryMan[]>;

@Injectable({ providedIn: 'root' })
export class DeliveryManService {
    public resourceUrl = SERVER_API_URL + 'api/delivery-men';

    constructor(private http: HttpClient) {}

    create(deliveryMan: IDeliveryMan): Observable<EntityResponseType> {
        return this.http.post<IDeliveryMan>(this.resourceUrl, deliveryMan, { observe: 'response' });
    }

    update(deliveryMan: IDeliveryMan): Observable<EntityResponseType> {
        return this.http.put<IDeliveryMan>(this.resourceUrl, deliveryMan, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDeliveryMan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDeliveryMan[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
