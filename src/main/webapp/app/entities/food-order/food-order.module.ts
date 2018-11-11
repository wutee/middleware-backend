import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyBackendv01SharedModule } from 'app/shared';
import {
    FoodOrderComponent,
    FoodOrderDetailComponent,
    FoodOrderUpdateComponent,
    FoodOrderDeletePopupComponent,
    FoodOrderDeleteDialogComponent,
    foodOrderRoute,
    foodOrderPopupRoute
} from './';

const ENTITY_STATES = [...foodOrderRoute, ...foodOrderPopupRoute];

@NgModule({
    imports: [PropsyBackendv01SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FoodOrderComponent,
        FoodOrderDetailComponent,
        FoodOrderUpdateComponent,
        FoodOrderDeleteDialogComponent,
        FoodOrderDeletePopupComponent
    ],
    entryComponents: [FoodOrderComponent, FoodOrderUpdateComponent, FoodOrderDeleteDialogComponent, FoodOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendv01FoodOrderModule {}
