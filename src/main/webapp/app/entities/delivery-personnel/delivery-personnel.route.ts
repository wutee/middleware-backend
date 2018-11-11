import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';
import { DeliveryPersonnelService } from './delivery-personnel.service';
import { DeliveryPersonnelComponent } from './delivery-personnel.component';
import { DeliveryPersonnelDetailComponent } from './delivery-personnel-detail.component';
import { DeliveryPersonnelUpdateComponent } from './delivery-personnel-update.component';
import { DeliveryPersonnelDeletePopupComponent } from './delivery-personnel-delete-dialog.component';
import { IDeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';

@Injectable({ providedIn: 'root' })
export class DeliveryPersonnelResolve implements Resolve<IDeliveryPersonnel> {
    constructor(private service: DeliveryPersonnelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((deliveryPersonnel: HttpResponse<DeliveryPersonnel>) => deliveryPersonnel.body));
        }
        return of(new DeliveryPersonnel());
    }
}

export const deliveryPersonnelRoute: Routes = [
    {
        path: 'delivery-personnel',
        component: DeliveryPersonnelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.deliveryPersonnel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'delivery-personnel/:id/view',
        component: DeliveryPersonnelDetailComponent,
        resolve: {
            deliveryPersonnel: DeliveryPersonnelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.deliveryPersonnel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'delivery-personnel/new',
        component: DeliveryPersonnelUpdateComponent,
        resolve: {
            deliveryPersonnel: DeliveryPersonnelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.deliveryPersonnel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'delivery-personnel/:id/edit',
        component: DeliveryPersonnelUpdateComponent,
        resolve: {
            deliveryPersonnel: DeliveryPersonnelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.deliveryPersonnel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deliveryPersonnelPopupRoute: Routes = [
    {
        path: 'delivery-personnel/:id/delete',
        component: DeliveryPersonnelDeletePopupComponent,
        resolve: {
            deliveryPersonnel: DeliveryPersonnelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propsyBackendv01App.deliveryPersonnel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
