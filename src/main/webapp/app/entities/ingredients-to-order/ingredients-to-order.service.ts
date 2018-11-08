import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';

type EntityResponseType = HttpResponse<IIngredientsToOrder>;
type EntityArrayResponseType = HttpResponse<IIngredientsToOrder[]>;

@Injectable({ providedIn: 'root' })
export class IngredientsToOrderService {
    public resourceUrl = SERVER_API_URL + 'api/ingredients-to-orders';

    constructor(private http: HttpClient) {}

    create(ingredientsToOrder: IIngredientsToOrder): Observable<EntityResponseType> {
        return this.http.post<IIngredientsToOrder>(this.resourceUrl, ingredientsToOrder, { observe: 'response' });
    }

    update(ingredientsToOrder: IIngredientsToOrder): Observable<EntityResponseType> {
        return this.http.put<IIngredientsToOrder>(this.resourceUrl, ingredientsToOrder, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIngredientsToOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIngredientsToOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
