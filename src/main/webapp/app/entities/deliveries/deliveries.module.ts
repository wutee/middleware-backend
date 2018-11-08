import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyTestSharedModule } from 'app/shared';
import {
    DeliveriesComponent,
    DeliveriesDetailComponent,
    DeliveriesUpdateComponent,
    DeliveriesDeletePopupComponent,
    DeliveriesDeleteDialogComponent,
    deliveriesRoute,
    deliveriesPopupRoute
} from './';

const ENTITY_STATES = [...deliveriesRoute, ...deliveriesPopupRoute];

@NgModule({
    imports: [PropsyTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeliveriesComponent,
        DeliveriesDetailComponent,
        DeliveriesUpdateComponent,
        DeliveriesDeleteDialogComponent,
        DeliveriesDeletePopupComponent
    ],
    entryComponents: [DeliveriesComponent, DeliveriesUpdateComponent, DeliveriesDeleteDialogComponent, DeliveriesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestDeliveriesModule {}
