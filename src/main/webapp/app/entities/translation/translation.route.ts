import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Translation } from 'app/shared/model/translation.model';
import { TranslationService } from './translation.service';
import { TranslationComponent } from './translation.component';
import { TranslationDetailComponent } from './translation-detail.component';
import { TranslationUpdateComponent } from './translation-update.component';
import { TranslationDeletePopupComponent } from './translation-delete-dialog.component';
import { ITranslation } from 'app/shared/model/translation.model';

@Injectable({ providedIn: 'root' })
export class TranslationResolve implements Resolve<ITranslation> {
    constructor(private service: TranslationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((translation: HttpResponse<Translation>) => translation.body));
        }
        return of(new Translation());
    }
}

export const translationRoute: Routes = [
    {
        path: 'translation',
        component: TranslationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.translation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'translation/:id/view',
        component: TranslationDetailComponent,
        resolve: {
            translation: TranslationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.translation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'translation/new',
        component: TranslationUpdateComponent,
        resolve: {
            translation: TranslationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.translation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'translation/:id/edit',
        component: TranslationUpdateComponent,
        resolve: {
            translation: TranslationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.translation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const translationPopupRoute: Routes = [
    {
        path: 'translation/:id/delete',
        component: TranslationDeletePopupComponent,
        resolve: {
            translation: TranslationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.translation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
