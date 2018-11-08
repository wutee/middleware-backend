import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoodInMenu } from 'app/shared/model/food-in-menu.model';

@Component({
    selector: 'jhi-food-in-menu-detail',
    templateUrl: './food-in-menu-detail.component.html'
})
export class FoodInMenuDetailComponent implements OnInit {
    foodInMenu: IFoodInMenu;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodInMenu }) => {
            this.foodInMenu = foodInMenu;
        });
    }

    previousState() {
        window.history.back();
    }
}
