import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PropsyBackendv01RestaurantModule } from './restaurant/restaurant.module';
import { PropsyBackendv01RestaurantWorkerModule } from './restaurant-worker/restaurant-worker.module';
import { PropsyBackendv01MenuModule } from './menu/menu.module';
import { PropsyBackendv01FoodModule } from './food/food.module';
import { PropsyBackendv01FoodOrderModule } from './food-order/food-order.module';
import { PropsyBackendv01PaymentModule } from './payment/payment.module';
import { PropsyBackendv01PaymentMethodModule } from './payment-method/payment-method.module';
import { PropsyBackendv01CustomerModule } from './customer/customer.module';
import { PropsyBackendv01TranslationModule } from './translation/translation.module';
import { PropsyBackendv01LanguageModule } from './language/language.module';
import { PropsyBackendv01IngredientModule } from './ingredient/ingredient.module';
import { PropsyBackendv01IngredientOrderModule } from './ingredient-order/ingredient-order.module';
import { PropsyBackendv01DeliveryPersonnelModule } from './delivery-personnel/delivery-personnel.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PropsyBackendv01RestaurantModule,
        PropsyBackendv01RestaurantWorkerModule,
        PropsyBackendv01MenuModule,
        PropsyBackendv01FoodModule,
        PropsyBackendv01FoodOrderModule,
        PropsyBackendv01PaymentModule,
        PropsyBackendv01PaymentMethodModule,
        PropsyBackendv01CustomerModule,
        PropsyBackendv01TranslationModule,
        PropsyBackendv01LanguageModule,
        PropsyBackendv01IngredientModule,
        PropsyBackendv01IngredientOrderModule,
        PropsyBackendv01DeliveryPersonnelModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyBackendv01EntityModule {}
