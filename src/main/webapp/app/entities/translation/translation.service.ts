import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITranslation } from 'app/shared/model/translation.model';

type EntityResponseType = HttpResponse<ITranslation>;
type EntityArrayResponseType = HttpResponse<ITranslation[]>;

@Injectable({ providedIn: 'root' })
export class TranslationService {
    public resourceUrl = SERVER_API_URL + 'api/translations';

    constructor(private http: HttpClient) {}

    create(translation: ITranslation): Observable<EntityResponseType> {
        return this.http.post<ITranslation>(this.resourceUrl, translation, { observe: 'response' });
    }

    update(translation: ITranslation): Observable<EntityResponseType> {
        return this.http.put<ITranslation>(this.resourceUrl, translation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITranslation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITranslation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
