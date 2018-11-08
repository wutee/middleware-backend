import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyTestSharedModule } from 'app/shared';
import {
    FoodInMenuComponent,
    FoodInMenuDetailComponent,
    FoodInMenuUpdateComponent,
    FoodInMenuDeletePopupComponent,
    FoodInMenuDeleteDialogComponent,
    foodInMenuRoute,
    foodInMenuPopupRoute
} from './';

const ENTITY_STATES = [...foodInMenuRoute, ...foodInMenuPopupRoute];

@NgModule({
    imports: [PropsyTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FoodInMenuComponent,
        FoodInMenuDetailComponent,
        FoodInMenuUpdateComponent,
        FoodInMenuDeleteDialogComponent,
        FoodInMenuDeletePopupComponent
    ],
    entryComponents: [FoodInMenuComponent, FoodInMenuUpdateComponent, FoodInMenuDeleteDialogComponent, FoodInMenuDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestFoodInMenuModule {}
