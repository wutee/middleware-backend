import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Translations } from 'app/shared/model/translations.model';
import { TranslationsService } from './translations.service';
import { TranslationsComponent } from './translations.component';
import { TranslationsDetailComponent } from './translations-detail.component';
import { TranslationsUpdateComponent } from './translations-update.component';
import { TranslationsDeletePopupComponent } from './translations-delete-dialog.component';
import { ITranslations } from 'app/shared/model/translations.model';

@Injectable({ providedIn: 'root' })
export class TranslationsResolve implements Resolve<ITranslations> {
    constructor(private service: TranslationsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((translations: HttpResponse<Translations>) => translations.body));
        }
        return of(new Translations());
    }
}

export const translationsRoute: Routes = [
    {
        path: 'translations',
        component: TranslationsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Translations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'translations/:id/view',
        component: TranslationsDetailComponent,
        resolve: {
            translations: TranslationsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Translations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'translations/new',
        component: TranslationsUpdateComponent,
        resolve: {
            translations: TranslationsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Translations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'translations/:id/edit',
        component: TranslationsUpdateComponent,
        resolve: {
            translations: TranslationsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Translations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const translationsPopupRoute: Routes = [
    {
        path: 'translations/:id/delete',
        component: TranslationsDeletePopupComponent,
        resolve: {
            translations: TranslationsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Translations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
