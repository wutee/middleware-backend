import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFood } from 'app/shared/model/food.model';
import { FoodService } from './food.service';
import { IFoodInOrder } from 'app/shared/model/food-in-order.model';
import { FoodInOrderService } from 'app/entities/food-in-order';
import { IFoodInMenu } from 'app/shared/model/food-in-menu.model';
import { FoodInMenuService } from 'app/entities/food-in-menu';

@Component({
    selector: 'jhi-food-update',
    templateUrl: './food-update.component.html'
})
export class FoodUpdateComponent implements OnInit {
    food: IFood;
    isSaving: boolean;

    foodinorders: IFoodInOrder[];

    foodinmenus: IFoodInMenu[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private foodService: FoodService,
        private foodInOrderService: FoodInOrderService,
        private foodInMenuService: FoodInMenuService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ food }) => {
            this.food = food;
        });
        this.foodInOrderService.query().subscribe(
            (res: HttpResponse<IFoodInOrder[]>) => {
                this.foodinorders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.foodInMenuService.query().subscribe(
            (res: HttpResponse<IFoodInMenu[]>) => {
                this.foodinmenus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.food.id !== undefined) {
            this.subscribeToSaveResponse(this.foodService.update(this.food));
        } else {
            this.subscribeToSaveResponse(this.foodService.create(this.food));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFood>>) {
        result.subscribe((res: HttpResponse<IFood>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFoodInOrderById(index: number, item: IFoodInOrder) {
        return item.id;
    }

    trackFoodInMenuById(index: number, item: IFoodInMenu) {
        return item.id;
    }
}
