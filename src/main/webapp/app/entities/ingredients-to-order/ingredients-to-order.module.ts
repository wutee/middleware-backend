import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyTestSharedModule } from 'app/shared';
import {
    IngredientsToOrderComponent,
    IngredientsToOrderDetailComponent,
    IngredientsToOrderUpdateComponent,
    IngredientsToOrderDeletePopupComponent,
    IngredientsToOrderDeleteDialogComponent,
    ingredientsToOrderRoute,
    ingredientsToOrderPopupRoute
} from './';

const ENTITY_STATES = [...ingredientsToOrderRoute, ...ingredientsToOrderPopupRoute];

@NgModule({
    imports: [PropsyTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IngredientsToOrderComponent,
        IngredientsToOrderDetailComponent,
        IngredientsToOrderUpdateComponent,
        IngredientsToOrderDeleteDialogComponent,
        IngredientsToOrderDeletePopupComponent
    ],
    entryComponents: [
        IngredientsToOrderComponent,
        IngredientsToOrderUpdateComponent,
        IngredientsToOrderDeleteDialogComponent,
        IngredientsToOrderDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestIngredientsToOrderModule {}
