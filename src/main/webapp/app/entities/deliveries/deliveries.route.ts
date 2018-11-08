import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Deliveries } from 'app/shared/model/deliveries.model';
import { DeliveriesService } from './deliveries.service';
import { DeliveriesComponent } from './deliveries.component';
import { DeliveriesDetailComponent } from './deliveries-detail.component';
import { DeliveriesUpdateComponent } from './deliveries-update.component';
import { DeliveriesDeletePopupComponent } from './deliveries-delete-dialog.component';
import { IDeliveries } from 'app/shared/model/deliveries.model';

@Injectable({ providedIn: 'root' })
export class DeliveriesResolve implements Resolve<IDeliveries> {
    constructor(private service: DeliveriesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((deliveries: HttpResponse<Deliveries>) => deliveries.body));
        }
        return of(new Deliveries());
    }
}

export const deliveriesRoute: Routes = [
    {
        path: 'deliveries',
        component: DeliveriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deliveries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deliveries/:id/view',
        component: DeliveriesDetailComponent,
        resolve: {
            deliveries: DeliveriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deliveries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deliveries/new',
        component: DeliveriesUpdateComponent,
        resolve: {
            deliveries: DeliveriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deliveries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deliveries/:id/edit',
        component: DeliveriesUpdateComponent,
        resolve: {
            deliveries: DeliveriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deliveries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deliveriesPopupRoute: Routes = [
    {
        path: 'deliveries/:id/delete',
        component: DeliveriesDeletePopupComponent,
        resolve: {
            deliveries: DeliveriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deliveries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
