import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIngredientOrder } from 'app/shared/model/ingredient-order.model';

type EntityResponseType = HttpResponse<IIngredientOrder>;
type EntityArrayResponseType = HttpResponse<IIngredientOrder[]>;

@Injectable({ providedIn: 'root' })
export class IngredientOrderService {
    public resourceUrl = SERVER_API_URL + 'api/ingredient-orders';

    constructor(private http: HttpClient) {}

    create(ingredientOrder: IIngredientOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ingredientOrder);
        return this.http
            .post<IIngredientOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(ingredientOrder: IIngredientOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ingredientOrder);
        return this.http
            .put<IIngredientOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIngredientOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIngredientOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(ingredientOrder: IIngredientOrder): IIngredientOrder {
        const copy: IIngredientOrder = Object.assign({}, ingredientOrder, {
            date: ingredientOrder.date != null && ingredientOrder.date.isValid() ? ingredientOrder.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((ingredientOrder: IIngredientOrder) => {
            ingredientOrder.date = ingredientOrder.date != null ? moment(ingredientOrder.date) : null;
        });
        return res;
    }
}
