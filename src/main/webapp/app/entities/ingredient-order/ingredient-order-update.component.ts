import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IIngredientOrder } from 'app/shared/model/ingredient-order.model';
import { IngredientOrderService } from './ingredient-order.service';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient';

@Component({
    selector: 'jhi-ingredient-order-update',
    templateUrl: './ingredient-order-update.component.html'
})
export class IngredientOrderUpdateComponent implements OnInit {
    ingredientOrder: IIngredientOrder;
    isSaving: boolean;

    ingredients: IIngredient[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private ingredientOrderService: IngredientOrderService,
        private ingredientService: IngredientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ingredientOrder }) => {
            this.ingredientOrder = ingredientOrder;
        });
        this.ingredientService.query().subscribe(
            (res: HttpResponse<IIngredient[]>) => {
                this.ingredients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackIngredientById(index: number, item: IIngredient) {
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
