import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';
import { IngredientsToOrderService } from './ingredients-to-order.service';
import { IngredientsToOrderComponent } from './ingredients-to-order.component';
import { IngredientsToOrderDetailComponent } from './ingredients-to-order-detail.component';
import { IngredientsToOrderUpdateComponent } from './ingredients-to-order-update.component';
import { IngredientsToOrderDeletePopupComponent } from './ingredients-to-order-delete-dialog.component';
import { IIngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';

@Injectable({ providedIn: 'root' })
export class IngredientsToOrderResolve implements Resolve<IIngredientsToOrder> {
    constructor(private service: IngredientsToOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((ingredientsToOrder: HttpResponse<IngredientsToOrder>) => ingredientsToOrder.body));
        }
        return of(new IngredientsToOrder());
    }
}

export const ingredientsToOrderRoute: Routes = [
    {
        path: 'ingredients-to-order',
        component: IngredientsToOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientsToOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingredients-to-order/:id/view',
        component: IngredientsToOrderDetailComponent,
        resolve: {
            ingredientsToOrder: IngredientsToOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientsToOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingredients-to-order/new',
        component: IngredientsToOrderUpdateComponent,
        resolve: {
            ingredientsToOrder: IngredientsToOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientsToOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingredients-to-order/:id/edit',
        component: IngredientsToOrderUpdateComponent,
        resolve: {
            ingredientsToOrder: IngredientsToOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientsToOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ingredientsToOrderPopupRoute: Routes = [
    {
        path: 'ingredients-to-order/:id/delete',
        component: IngredientsToOrderDeletePopupComponent,
        resolve: {
            ingredientsToOrder: IngredientsToOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientsToOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
