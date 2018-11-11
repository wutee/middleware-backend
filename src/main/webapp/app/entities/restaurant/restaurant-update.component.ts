import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from './restaurant.service';
import { IRestaurantWorker } from 'app/shared/model/restaurant-worker.model';
import { RestaurantWorkerService } from 'app/entities/restaurant-worker';

@Component({
    selector: 'jhi-restaurant-update',
    templateUrl: './restaurant-update.component.html'
})
export class RestaurantUpdateComponent implements OnInit {
    restaurant: IRestaurant;
    isSaving: boolean;

    restaurantworkers: IRestaurantWorker[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private restaurantService: RestaurantService,
        private restaurantWorkerService: RestaurantWorkerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ restaurant }) => {
            this.restaurant = restaurant;
        });
        this.restaurantWorkerService.query().subscribe(
            (res: HttpResponse<IRestaurantWorker[]>) => {
                this.restaurantworkers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.restaurant.id !== undefined) {
            this.subscribeToSaveResponse(this.restaurantService.update(this.restaurant));
        } else {
            this.subscribeToSaveResponse(this.restaurantService.create(this.restaurant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurant>>) {
        result.subscribe((res: HttpResponse<IRestaurant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRestaurantWorkerById(index: number, item: IRestaurantWorker) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
