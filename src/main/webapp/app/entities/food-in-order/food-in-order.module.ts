import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyTestSharedModule } from 'app/shared';
import {
    FoodInOrderComponent,
    FoodInOrderDetailComponent,
    FoodInOrderUpdateComponent,
    FoodInOrderDeletePopupComponent,
    FoodInOrderDeleteDialogComponent,
    foodInOrderRoute,
    foodInOrderPopupRoute
} from './';

const ENTITY_STATES = [...foodInOrderRoute, ...foodInOrderPopupRoute];

@NgModule({
    imports: [PropsyTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FoodInOrderComponent,
        FoodInOrderDetailComponent,
        FoodInOrderUpdateComponent,
        FoodInOrderDeleteDialogComponent,
        FoodInOrderDeletePopupComponent
    ],
    entryComponents: [FoodInOrderComponent, FoodInOrderUpdateComponent, FoodInOrderDeleteDialogComponent, FoodInOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestFoodInOrderModule {}
