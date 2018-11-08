import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeliveries } from 'app/shared/model/deliveries.model';

type EntityResponseType = HttpResponse<IDeliveries>;
type EntityArrayResponseType = HttpResponse<IDeliveries[]>;

@Injectable({ providedIn: 'root' })
export class DeliveriesService {
    public resourceUrl = SERVER_API_URL + 'api/deliveries';

    constructor(private http: HttpClient) {}

    create(deliveries: IDeliveries): Observable<EntityResponseType> {
        return this.http.post<IDeliveries>(this.resourceUrl, deliveries, { observe: 'response' });
    }

    update(deliveries: IDeliveries): Observable<EntityResponseType> {
        return this.http.put<IDeliveries>(this.resourceUrl, deliveries, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDeliveries>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDeliveries[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
