import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyBackendv01SharedModule } from 'app/shared';
import {
    PaymentMethodComponent,
    PaymentMethodDetailComponent,
    PaymentMethodUpdateComponent,
    PaymentMethodDeletePopupComponent,
    PaymentMethodDeleteDialogComponent,
    paymentMethodRoute,
    paymentMethodPopupRoute
} from './';

const ENTITY_STATES = [...paymentMethodRoute, ...paymentMethodPopupRoute];

@NgModule({
    imports: [PropsyBackendv01SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PaymentMethodComponent,
        PaymentMethodDetailComponent,
        PaymentMethodUpdateComponent,
        PaymentMethodDeleteDialogComponent,
        PaymentMethodDeletePopupComponent
    ],
    entryComponents: [
        PaymentMethodComponent,
        PaymentMethodUpdateComponent,
        PaymentMethodDeleteDialogComponent,
        PaymentMethodDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendv01PaymentMethodModule {}
