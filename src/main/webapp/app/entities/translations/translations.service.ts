import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITranslations } from 'app/shared/model/translations.model';

type EntityResponseType = HttpResponse<ITranslations>;
type EntityArrayResponseType = HttpResponse<ITranslations[]>;

@Injectable({ providedIn: 'root' })
export class TranslationsService {
    public resourceUrl = SERVER_API_URL + 'api/translations';

    constructor(private http: HttpClient) {}

    create(translations: ITranslations): Observable<EntityResponseType> {
        return this.http.post<ITranslations>(this.resourceUrl, translations, { observe: 'response' });
    }

    update(translations: ITranslations): Observable<EntityResponseType> {
        return this.http.put<ITranslations>(this.resourceUrl, translations, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITranslations>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITranslations[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
