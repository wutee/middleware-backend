import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IngredientOrder } from 'app/shared/model/ingredient-order.model';
import { IngredientOrderService } from './ingredient-order.service';
import { IngredientOrderComponent } from './ingredient-order.component';
import { IngredientOrderDetailComponent } from './ingredient-order-detail.component';
import { IngredientOrderUpdateComponent } from './ingredient-order-update.component';
import { IngredientOrderDeletePopupComponent } from './ingredient-order-delete-dialog.component';
import { IIngredientOrder } from 'app/shared/model/ingredient-order.model';

@Injectable({ providedIn: 'root' })
export class IngredientOrderResolve implements Resolve<IIngredientOrder> {
    constructor(private service: IngredientOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((ingredientOrder: HttpResponse<IngredientOrder>) => ingredientOrder.body));
        }
        return of(new IngredientOrder());
    }
}

export const ingredientOrderRoute: Routes = [
    {
        path: 'ingredient-order',
        component: IngredientOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingredient-order/:id/view',
        component: IngredientOrderDetailComponent,
        resolve: {
            ingredientOrder: IngredientOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingredient-order/new',
        component: IngredientOrderUpdateComponent,
        resolve: {
            ingredientOrder: IngredientOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingredient-order/:id/edit',
        component: IngredientOrderUpdateComponent,
        resolve: {
            ingredientOrder: IngredientOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ingredientOrderPopupRoute: Routes = [
    {
        path: 'ingredient-order/:id/delete',
        component: IngredientOrderDeletePopupComponent,
        resolve: {
            ingredientOrder: IngredientOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
