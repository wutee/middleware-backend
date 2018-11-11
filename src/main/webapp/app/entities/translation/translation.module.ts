import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyBackendv01SharedModule } from 'app/shared';
import {
    TranslationComponent,
    TranslationDetailComponent,
    TranslationUpdateComponent,
    TranslationDeletePopupComponent,
    TranslationDeleteDialogComponent,
    translationRoute,
    translationPopupRoute
} from './';

const ENTITY_STATES = [...translationRoute, ...translationPopupRoute];

@NgModule({
    imports: [PropsyBackendv01SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TranslationComponent,
        TranslationDetailComponent,
        TranslationUpdateComponent,
        TranslationDeleteDialogComponent,
        TranslationDeletePopupComponent
    ],
    entryComponents: [TranslationComponent, TranslationUpdateComponent, TranslationDeleteDialogComponent, TranslationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendv01TranslationModule {}
