import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRestaurantWorker } from 'app/shared/model/restaurant-worker.model';

type EntityResponseType = HttpResponse<IRestaurantWorker>;
type EntityArrayResponseType = HttpResponse<IRestaurantWorker[]>;

@Injectable({ providedIn: 'root' })
export class RestaurantWorkerService {
    public resourceUrl = SERVER_API_URL + 'api/restaurant-workers';

    constructor(private http: HttpClient) {}

    create(restaurantWorker: IRestaurantWorker): Observable<EntityResponseType> {
        return this.http.post<IRestaurantWorker>(this.resourceUrl, restaurantWorker, { observe: 'response' });
    }

    update(restaurantWorker: IRestaurantWorker): Observable<EntityResponseType> {
        return this.http.put<IRestaurantWorker>(this.resourceUrl, restaurantWorker, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRestaurantWorker>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRestaurantWorker[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
