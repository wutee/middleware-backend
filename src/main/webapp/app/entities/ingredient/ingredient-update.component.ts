import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from './ingredient.service';
import { IIngredientOrder } from 'app/shared/model/ingredient-order.model';
import { IngredientOrderService } from 'app/entities/ingredient-order';

@Component({
    selector: 'jhi-ingredient-update',
    templateUrl: './ingredient-update.component.html'
})
export class IngredientUpdateComponent implements OnInit {
    ingredient: IIngredient;
    isSaving: boolean;

    ingredientorders: IIngredientOrder[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private ingredientService: IngredientService,
        private ingredientOrderService: IngredientOrderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ingredient }) => {
            this.ingredient = ingredient;
        });
        this.ingredientOrderService.query().subscribe(
            (res: HttpResponse<IIngredientOrder[]>) => {
                this.ingredientorders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ingredient.id !== undefined) {
            this.subscribeToSaveResponse(this.ingredientService.update(this.ingredient));
        } else {
            this.subscribeToSaveResponse(this.ingredientService.create(this.ingredient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIngredient>>) {
        result.subscribe((res: HttpResponse<IIngredient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIngredientOrderById(index: number, item: IIngredientOrder) {
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
