import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FoodInMenu } from 'app/shared/model/food-in-menu.model';
import { FoodInMenuService } from './food-in-menu.service';
import { FoodInMenuComponent } from './food-in-menu.component';
import { FoodInMenuDetailComponent } from './food-in-menu-detail.component';
import { FoodInMenuUpdateComponent } from './food-in-menu-update.component';
import { FoodInMenuDeletePopupComponent } from './food-in-menu-delete-dialog.component';
import { IFoodInMenu } from 'app/shared/model/food-in-menu.model';

@Injectable({ providedIn: 'root' })
export class FoodInMenuResolve implements Resolve<IFoodInMenu> {
    constructor(private service: FoodInMenuService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((foodInMenu: HttpResponse<FoodInMenu>) => foodInMenu.body));
        }
        return of(new FoodInMenu());
    }
}

export const foodInMenuRoute: Routes = [
    {
        path: 'food-in-menu',
        component: FoodInMenuComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInMenus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-in-menu/:id/view',
        component: FoodInMenuDetailComponent,
        resolve: {
            foodInMenu: FoodInMenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInMenus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-in-menu/new',
        component: FoodInMenuUpdateComponent,
        resolve: {
            foodInMenu: FoodInMenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInMenus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-in-menu/:id/edit',
        component: FoodInMenuUpdateComponent,
        resolve: {
            foodInMenu: FoodInMenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInMenus'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const foodInMenuPopupRoute: Routes = [
    {
        path: 'food-in-menu/:id/delete',
        component: FoodInMenuDeletePopupComponent,
        resolve: {
            foodInMenu: FoodInMenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodInMenus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
