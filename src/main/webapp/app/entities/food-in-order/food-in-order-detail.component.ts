import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoodInOrder } from 'app/shared/model/food-in-order.model';

@Component({
    selector: 'jhi-food-in-order-detail',
    templateUrl: './food-in-order-detail.component.html'
})
export class FoodInOrderDetailComponent implements OnInit {
    foodInOrder: IFoodInOrder;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodInOrder }) => {
            this.foodInOrder = foodInOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
