import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFoodInMenu } from 'app/shared/model/food-in-menu.model';

type EntityResponseType = HttpResponse<IFoodInMenu>;
type EntityArrayResponseType = HttpResponse<IFoodInMenu[]>;

@Injectable({ providedIn: 'root' })
export class FoodInMenuService {
    public resourceUrl = SERVER_API_URL + 'api/food-in-menus';

    constructor(private http: HttpClient) {}

    create(foodInMenu: IFoodInMenu): Observable<EntityResponseType> {
        return this.http.post<IFoodInMenu>(this.resourceUrl, foodInMenu, { observe: 'response' });
    }

    update(foodInMenu: IFoodInMenu): Observable<EntityResponseType> {
        return this.http.put<IFoodInMenu>(this.resourceUrl, foodInMenu, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFoodInMenu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFoodInMenu[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
