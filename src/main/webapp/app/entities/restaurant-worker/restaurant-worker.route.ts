import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RestaurantWorker } from 'app/shared/model/restaurant-worker.model';
import { RestaurantWorkerService } from './restaurant-worker.service';
import { RestaurantWorkerComponent } from './restaurant-worker.component';
import { RestaurantWorkerDetailComponent } from './restaurant-worker-detail.component';
import { RestaurantWorkerUpdateComponent } from './restaurant-worker-update.component';
import { RestaurantWorkerDeletePopupComponent } from './restaurant-worker-delete-dialog.component';
import { IRestaurantWorker } from 'app/shared/model/restaurant-worker.model';

@Injectable({ providedIn: 'root' })
export class RestaurantWorkerResolve implements Resolve<IRestaurantWorker> {
    constructor(private service: RestaurantWorkerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((restaurantWorker: HttpResponse<RestaurantWorker>) => restaurantWorker.body));
        }
        return of(new RestaurantWorker());
    }
}

export const restaurantWorkerRoute: Routes = [
    {
        path: 'restaurant-worker',
        component: RestaurantWorkerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RestaurantWorkers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant-worker/:id/view',
        component: RestaurantWorkerDetailComponent,
        resolve: {
            restaurantWorker: RestaurantWorkerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RestaurantWorkers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant-worker/new',
        component: RestaurantWorkerUpdateComponent,
        resolve: {
            restaurantWorker: RestaurantWorkerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RestaurantWorkers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant-worker/:id/edit',
        component: RestaurantWorkerUpdateComponent,
        resolve: {
            restaurantWorker: RestaurantWorkerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RestaurantWorkers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const restaurantWorkerPopupRoute: Routes = [
    {
        path: 'restaurant-worker/:id/delete',
        component: RestaurantWorkerDeletePopupComponent,
        resolve: {
            restaurantWorker: RestaurantWorkerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RestaurantWorkers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
