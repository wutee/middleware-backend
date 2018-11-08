import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IIngredientOrder } from 'app/shared/model/ingredient-order.model';
import { IngredientOrderService } from './ingredient-order.service';

@Component({
    selector: 'jhi-ingredient-order-update',
    templateUrl: './ingredient-order-update.component.html'
})
export class IngredientOrderUpdateComponent implements OnInit {
    ingredientOrder: IIngredientOrder;
    isSaving: boolean;
    dateDp: any;

    constructor(private ingredientOrderService: IngredientOrderService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ingredientOrder }) => {
            this.ingredientOrder = ingredientOrder;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ingredientOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.ingredientOrderService.update(this.ingredientOrder));
        } else {
            this.subscribeToSaveResponse(this.ingredientOrderService.create(this.ingredientOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIngredientOrder>>) {
        result.subscribe((res: HttpResponse<IIngredientOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
