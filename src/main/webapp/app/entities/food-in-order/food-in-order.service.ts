import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFoodInOrder } from 'app/shared/model/food-in-order.model';

type EntityResponseType = HttpResponse<IFoodInOrder>;
type EntityArrayResponseType = HttpResponse<IFoodInOrder[]>;

@Injectable({ providedIn: 'root' })
export class FoodInOrderService {
    public resourceUrl = SERVER_API_URL + 'api/food-in-orders';

    constructor(private http: HttpClient) {}

    create(foodInOrder: IFoodInOrder): Observable<EntityResponseType> {
        return this.http.post<IFoodInOrder>(this.resourceUrl, foodInOrder, { observe: 'response' });
    }

    update(foodInOrder: IFoodInOrder): Observable<EntityResponseType> {
        return this.http.put<IFoodInOrder>(this.resourceUrl, foodInOrder, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFoodInOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFoodInOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
