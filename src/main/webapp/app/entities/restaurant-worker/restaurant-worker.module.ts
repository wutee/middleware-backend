import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropsyTestSharedModule } from 'app/shared';
import {
    RestaurantWorkerComponent,
    RestaurantWorkerDetailComponent,
    RestaurantWorkerUpdateComponent,
    RestaurantWorkerDeletePopupComponent,
    RestaurantWorkerDeleteDialogComponent,
    restaurantWorkerRoute,
    restaurantWorkerPopupRoute
} from './';

const ENTITY_STATES = [...restaurantWorkerRoute, ...restaurantWorkerPopupRoute];

@NgModule({
    imports: [PropsyTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RestaurantWorkerComponent,
        RestaurantWorkerDetailComponent,
        RestaurantWorkerUpdateComponent,
        RestaurantWorkerDeleteDialogComponent,
        RestaurantWorkerDeletePopupComponent
    ],
    entryComponents: [
        RestaurantWorkerComponent,
        RestaurantWorkerUpdateComponent,
        RestaurantWorkerDeleteDialogComponent,
        RestaurantWorkerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestRestaurantWorkerModule {}
