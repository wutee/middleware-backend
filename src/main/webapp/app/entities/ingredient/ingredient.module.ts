import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyBackendv01SharedModule } from 'app/shared';
import {
    IngredientComponent,
    IngredientDetailComponent,
    IngredientUpdateComponent,
    IngredientDeletePopupComponent,
    IngredientDeleteDialogComponent,
    ingredientRoute,
    ingredientPopupRoute
} from './';

const ENTITY_STATES = [...ingredientRoute, ...ingredientPopupRoute];

@NgModule({
    imports: [PropsyBackendv01SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IngredientComponent,
        IngredientDetailComponent,
        IngredientUpdateComponent,
        IngredientDeleteDialogComponent,
        IngredientDeletePopupComponent
    ],
    entryComponents: [IngredientComponent, IngredientUpdateComponent, IngredientDeleteDialogComponent, IngredientDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendv01IngredientModule {}
