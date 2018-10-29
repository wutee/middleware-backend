import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRestaurantWorker } from 'app/shared/model/restaurant-worker.model';
import { RestaurantWorkerService } from './restaurant-worker.service';

@Component({
    selector: 'jhi-restaurant-worker-update',
    templateUrl: './restaurant-worker-update.component.html'
})
export class RestaurantWorkerUpdateComponent implements OnInit {
    restaurantWorker: IRestaurantWorker;
    isSaving: boolean;

    constructor(private restaurantWorkerService: RestaurantWorkerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ restaurantWorker }) => {
            this.restaurantWorker = restaurantWorker;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.restaurantWorker.id !== undefined) {
            this.subscribeToSaveResponse(this.restaurantWorkerService.update(this.restaurantWorker));
        } else {
            this.subscribeToSaveResponse(this.restaurantWorkerService.create(this.restaurantWorker));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurantWorker>>) {
        result.subscribe((res: HttpResponse<IRestaurantWorker>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
