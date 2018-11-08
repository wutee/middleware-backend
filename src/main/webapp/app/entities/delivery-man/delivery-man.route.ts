import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeliveryMan } from 'app/shared/model/delivery-man.model';
import { DeliveryManService } from './delivery-man.service';
import { DeliveryManComponent } from './delivery-man.component';
import { DeliveryManDetailComponent } from './delivery-man-detail.component';
import { DeliveryManUpdateComponent } from './delivery-man-update.component';
import { DeliveryManDeletePopupComponent } from './delivery-man-delete-dialog.component';
import { IDeliveryMan } from 'app/shared/model/delivery-man.model';

@Injectable({ providedIn: 'root' })
export class DeliveryManResolve implements Resolve<IDeliveryMan> {
    constructor(private service: DeliveryManService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((deliveryMan: HttpResponse<DeliveryMan>) => deliveryMan.body));
        }
        return of(new DeliveryMan());
    }
}

export const deliveryManRoute: Routes = [
    {
        path: 'delivery-man',
        component: DeliveryManComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeliveryMen'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'delivery-man/:id/view',
        component: DeliveryManDetailComponent,
        resolve: {
            deliveryMan: DeliveryManResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeliveryMen'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'delivery-man/new',
        component: DeliveryManUpdateComponent,
        resolve: {
            deliveryMan: DeliveryManResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeliveryMen'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'delivery-man/:id/edit',
        component: DeliveryManUpdateComponent,
        resolve: {
            deliveryMan: DeliveryManResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeliveryMen'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deliveryManPopupRoute: Routes = [
    {
        path: 'delivery-man/:id/delete',
        component: DeliveryManDeletePopupComponent,
        resolve: {
            deliveryMan: DeliveryManResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeliveryMen'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
