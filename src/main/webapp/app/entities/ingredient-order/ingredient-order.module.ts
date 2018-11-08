import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyTestSharedModule } from 'app/shared';
import {
    IngredientOrderComponent,
    IngredientOrderDetailComponent,
    IngredientOrderUpdateComponent,
    IngredientOrderDeletePopupComponent,
    IngredientOrderDeleteDialogComponent,
    ingredientOrderRoute,
    ingredientOrderPopupRoute
} from './';

const ENTITY_STATES = [...ingredientOrderRoute, ...ingredientOrderPopupRoute];

@NgModule({
    imports: [PropsyTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IngredientOrderComponent,
        IngredientOrderDetailComponent,
        IngredientOrderUpdateComponent,
        IngredientOrderDeleteDialogComponent,
        IngredientOrderDeletePopupComponent
    ],
    entryComponents: [
        IngredientOrderComponent,
        IngredientOrderUpdateComponent,
        IngredientOrderDeleteDialogComponent,
        IngredientOrderDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestIngredientOrderModule {}
