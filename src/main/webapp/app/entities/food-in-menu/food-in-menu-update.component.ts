import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFoodInMenu } from 'app/shared/model/food-in-menu.model';
import { FoodInMenuService } from './food-in-menu.service';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';

@Component({
    selector: 'jhi-food-in-menu-update',
    templateUrl: './food-in-menu-update.component.html'
})
export class FoodInMenuUpdateComponent implements OnInit {
    foodInMenu: IFoodInMenu;
    isSaving: boolean;

    menus: IMenu[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private foodInMenuService: FoodInMenuService,
        private menuService: MenuService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ foodInMenu }) => {
            this.foodInMenu = foodInMenu;
        });
        this.menuService.query().subscribe(
            (res: HttpResponse<IMenu[]>) => {
                this.menus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.foodInMenu.id !== undefined) {
            this.subscribeToSaveResponse(this.foodInMenuService.update(this.foodInMenu));
        } else {
            this.subscribeToSaveResponse(this.foodInMenuService.create(this.foodInMenu));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFoodInMenu>>) {
        result.subscribe((res: HttpResponse<IFoodInMenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMenuById(index: number, item: IMenu) {
        return item.id;
    }
}
