import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PropsyTestRestaurantModule } from './restaurant/restaurant.module';
import { PropsyTestRestaurantWorkerModule } from './restaurant-worker/restaurant-worker.module';
import { PropsyTestMenuModule } from './menu/menu.module';
import { PropsyTestFoodInMenuModule } from './food-in-menu/food-in-menu.module';
import { PropsyTestFoodModule } from './food/food.module';
import { PropsyTestCustomerOrderModule } from './customer-order/customer-order.module';
import { PropsyTestFoodInOrderModule } from './food-in-order/food-in-order.module';
import { PropsyTestPaymentModule } from './payment/payment.module';
import { PropsyTestPaymentMethodModule } from './payment-method/payment-method.module';
import { PropsyTestCustomerModule } from './customer/customer.module';
import { PropsyTestTranslationsModule } from './translations/translations.module';
import { PropsyTestLanguageModule } from './language/language.module';
import { PropsyTestIngredientModule } from './ingredient/ingredient.module';
import { PropsyTestIngredientsToOrderModule } from './ingredients-to-order/ingredients-to-order.module';
import { PropsyTestIngredientOrderModule } from './ingredient-order/ingredient-order.module';
import { PropsyTestDeliveryManModule } from './delivery-man/delivery-man.module';
import { PropsyTestDeliveriesModule } from './deliveries/deliveries.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PropsyTestRestaurantModule,
        PropsyTestRestaurantWorkerModule,
        PropsyTestMenuModule,
        PropsyTestFoodInMenuModule,
        PropsyTestFoodModule,
        PropsyTestCustomerOrderModule,
        PropsyTestFoodInOrderModule,
        PropsyTestPaymentModule,
        PropsyTestPaymentMethodModule,
        PropsyTestCustomerModule,
        PropsyTestTranslationsModule,
        PropsyTestLanguageModule,
        PropsyTestIngredientModule,
        PropsyTestIngredientsToOrderModule,
        PropsyTestIngredientOrderModule,
        PropsyTestDeliveryManModule,
        PropsyTestDeliveriesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropsyTestEntityModule {}
