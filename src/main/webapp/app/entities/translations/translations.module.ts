import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyTestSharedModule } from 'app/shared';
import {
    TranslationsComponent,
    TranslationsDetailComponent,
    TranslationsUpdateComponent,
    TranslationsDeletePopupComponent,
    TranslationsDeleteDialogComponent,
    translationsRoute,
    translationsPopupRoute
} from './';

const ENTITY_STATES = [...translationsRoute, ...translationsPopupRoute];

@NgModule({
    imports: [PropsyTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TranslationsComponent,
        TranslationsDetailComponent,
        TranslationsUpdateComponent,
        TranslationsDeleteDialogComponent,
        TranslationsDeletePopupComponent
    ],
    entryComponents: [
        TranslationsComponent,
        TranslationsUpdateComponent,
        TranslationsDeleteDialogComponent,
        TranslationsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestTranslationsModule {}
