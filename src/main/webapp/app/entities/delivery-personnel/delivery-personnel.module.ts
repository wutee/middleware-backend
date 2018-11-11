import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyBackendv01SharedModule } from 'app/shared';
import {
    DeliveryPersonnelComponent,
    DeliveryPersonnelDetailComponent,
    DeliveryPersonnelUpdateComponent,
    DeliveryPersonnelDeletePopupComponent,
    DeliveryPersonnelDeleteDialogComponent,
    deliveryPersonnelRoute,
    deliveryPersonnelPopupRoute
} from './';

const ENTITY_STATES = [...deliveryPersonnelRoute, ...deliveryPersonnelPopupRoute];

@NgModule({
    imports: [PropsyBackendv01SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeliveryPersonnelComponent,
        DeliveryPersonnelDetailComponent,
        DeliveryPersonnelUpdateComponent,
        DeliveryPersonnelDeleteDialogComponent,
        DeliveryPersonnelDeletePopupComponent
    ],
    entryComponents: [
        DeliveryPersonnelComponent,
        DeliveryPersonnelUpdateComponent,
        DeliveryPersonnelDeleteDialogComponent,
        DeliveryPersonnelDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendv01DeliveryPersonnelModule {}
