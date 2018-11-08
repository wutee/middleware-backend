import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyTestSharedModule } from 'app/shared';
import {
    DeliveryManComponent,
    DeliveryManDetailComponent,
    DeliveryManUpdateComponent,
    DeliveryManDeletePopupComponent,
    DeliveryManDeleteDialogComponent,
    deliveryManRoute,
    deliveryManPopupRoute
} from './';

const ENTITY_STATES = [...deliveryManRoute, ...deliveryManPopupRoute];

@NgModule({
    imports: [PropsyTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeliveryManComponent,
        DeliveryManDetailComponent,
        DeliveryManUpdateComponent,
        DeliveryManDeleteDialogComponent,
        DeliveryManDeletePopupComponent
    ],
    entryComponents: [DeliveryManComponent, DeliveryManUpdateComponent, DeliveryManDeleteDialogComponent, DeliveryManDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestDeliveryManModule {}
