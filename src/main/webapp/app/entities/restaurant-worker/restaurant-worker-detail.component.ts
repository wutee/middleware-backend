import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRestaurantWorker } from 'app/shared/model/restaurant-worker.model';

@Component({
    selector: 'jhi-restaurant-worker-detail',
    templateUrl: './restaurant-worker-detail.component.html'
})
export class RestaurantWorkerDetailComponent implements OnInit {
    restaurantWorker: IRestaurantWorker;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ restaurantWorker }) => {
            this.restaurantWorker = restaurantWorker;
        });
    }

    previousState() {
        window.history.back();
    }
}
