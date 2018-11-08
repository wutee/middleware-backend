import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IIngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';
import { IngredientsToOrderService } from './ingredients-to-order.service';

@Component({
    selector: 'jhi-ingredients-to-order-update',
    templateUrl: './ingredients-to-order-update.component.html'
})
export class IngredientsToOrderUpdateComponent implements OnInit {
    ingredientsToOrder: IIngredientsToOrder;
    isSaving: boolean;

    constructor(private ingredientsToOrderService: IngredientsToOrderService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ingredientsToOrder }) => {
            this.ingredientsToOrder = ingredientsToOrder;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ingredientsToOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.ingredientsToOrderService.update(this.ingredientsToOrder));
        } else {
            this.subscribeToSaveResponse(this.ingredientsToOrderService.create(this.ingredientsToOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIngredientsToOrder>>) {
        result.subscribe((res: HttpResponse<IIngredientsToOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
