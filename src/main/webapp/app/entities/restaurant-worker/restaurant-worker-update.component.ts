import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRestaurantWorker } from 'app/shared/model/restaurant-worker.model';
import { RestaurantWorkerService } from './restaurant-worker.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
    selector: 'jhi-restaurant-worker-update',
    templateUrl: './restaurant-worker-update.component.html'
})
export class RestaurantWorkerUpdateComponent implements OnInit {
    restaurantWorker: IRestaurantWorker;
    isSaving: boolean;

    restaurants: IRestaurant[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private restaurantWorkerService: RestaurantWorkerService,
        private restaurantService: RestaurantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ restaurantWorker }) => {
            this.restaurantWorker = restaurantWorker;
        });
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRestaurantById(index: number, item: IRestaurant) {
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
