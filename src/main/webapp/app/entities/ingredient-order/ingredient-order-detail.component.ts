import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIngredientOrder } from 'app/shared/model/ingredient-order.model';

@Component({
    selector: 'jhi-ingredient-order-detail',
    templateUrl: './ingredient-order-detail.component.html'
})
export class IngredientOrderDetailComponent implements OnInit {
    ingredientOrder: IIngredientOrder;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ingredientOrder }) => {
            this.ingredientOrder = ingredientOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
