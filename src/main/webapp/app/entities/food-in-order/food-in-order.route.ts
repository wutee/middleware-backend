import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FoodInOrder } from 'app/shared/model/food-in-order.model';
import { FoodInOrderService } from './food-in-order.service';
import { FoodInOrderComponent } from './food-in-order.component';
import { FoodInOrderDetailComponent } from './food-in-order-detail.component';
import { FoodInOrderUpdateComponent } from './food-in-order-update.component';
import { FoodInOrderDeletePopupComponent } from './food-in-order-delete-dialog.component';
import { IFoodInOrder } from 'app/shared/model/food-in-order.model';

@Injectable({ providedIn: 'root' })
export class FoodInOrderResolve implements Resolve<IFoodInOrder> {
    constructor(private service: FoodInOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((foodInOrder: HttpResponse<FoodInOrder>) => foodInOrder.body));
        }
        return of(new FoodInOrder());
    }
}

export const foodInOrderRoute: Routes = [
    {
        path: 'food-in-order',
        component: FoodInOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-in-order/:id/view',
        component: FoodInOrderDetailComponent,
        resolve: {
            foodInOrder: FoodInOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-in-order/new',
        component: FoodInOrderUpdateComponent,
        resolve: {
            foodInOrder: FoodInOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-in-order/:id/edit',
        component: FoodInOrderUpdateComponent,
        resolve: {
            foodInOrder: FoodInOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const foodInOrderPopupRoute: Routes = [
    {
        path: 'food-in-order/:id/delete',
        component: FoodInOrderDeletePopupComponent,
        resolve: {
            foodInOrder: FoodInOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
