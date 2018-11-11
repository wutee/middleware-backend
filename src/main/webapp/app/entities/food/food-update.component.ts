import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFood } from 'app/shared/model/food.model';
import { FoodService } from './food.service';
import { ITranslation } from 'app/shared/model/translation.model';
import { TranslationService } from 'app/entities/translation';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';
import { IFoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from 'app/entities/food-order';

@Component({
    selector: 'jhi-food-update',
    templateUrl: './food-update.component.html'
})
export class FoodUpdateComponent implements OnInit {
    food: IFood;
    isSaving: boolean;

    translations: ITranslation[];

    menus: IMenu[];

    foodorders: IFoodOrder[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private foodService: FoodService,
        private translationService: TranslationService,
        private menuService: MenuService,
        private foodOrderService: FoodOrderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ food }) => {
            this.food = food;
        });
        this.translationService.query().subscribe(
            (res: HttpResponse<ITranslation[]>) => {
                this.translations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.menuService.query().subscribe(
            (res: HttpResponse<IMenu[]>) => {
                this.menus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.foodOrderService.query().subscribe(
            (res: HttpResponse<IFoodOrder[]>) => {
                this.foodorders = res.body;
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

    trackTranslationById(index: number, item: ITranslation) {
        return item.id;
    }

    trackMenuById(index: number, item: IMenu) {
        return item.id;
    }

    trackFoodOrderById(index: number, item: IFoodOrder) {
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
