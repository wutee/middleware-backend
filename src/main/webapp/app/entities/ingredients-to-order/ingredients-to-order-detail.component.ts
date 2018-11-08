import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';

@Component({
    selector: 'jhi-ingredients-to-order-detail',
    templateUrl: './ingredients-to-order-detail.component.html'
})
export class IngredientsToOrderDetailComponent implements OnInit {
    ingredientsToOrder: IIngredientsToOrder;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ingredientsToOrder }) => {
            this.ingredientsToOrder = ingredientsToOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
