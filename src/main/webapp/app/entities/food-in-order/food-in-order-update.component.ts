import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFoodInOrder } from 'app/shared/model/food-in-order.model';
import { FoodInOrderService } from './food-in-order.service';
import { ICustomerOrder } from 'app/shared/model/customer-order.model';
import { CustomerOrderService } from 'app/entities/customer-order';

@Component({
    selector: 'jhi-food-in-order-update',
    templateUrl: './food-in-order-update.component.html'
})
export class FoodInOrderUpdateComponent implements OnInit {
    foodInOrder: IFoodInOrder;
    isSaving: boolean;

    customerorders: ICustomerOrder[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private foodInOrderService: FoodInOrderService,
        private customerOrderService: CustomerOrderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ foodInOrder }) => {
            this.foodInOrder = foodInOrder;
        });
        this.customerOrderService.query().subscribe(
            (res: HttpResponse<ICustomerOrder[]>) => {
                this.customerorders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.foodInOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.foodInOrderService.update(this.foodInOrder));
        } else {
            this.subscribeToSaveResponse(this.foodInOrderService.create(this.foodInOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFoodInOrder>>) {
        result.subscribe((res: HttpResponse<IFoodInOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomerOrderById(index: number, item: ICustomerOrder) {
        return item.id;
    }
}
