import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFoodInMenu } from 'app/shared/model/food-in-menu.model';
import { Principal } from 'app/core';
import { FoodInMenuService } from './food-in-menu.service';

@Component({
    selector: 'jhi-food-in-menu',
    templateUrl: './food-in-menu.component.html'
})
export class FoodInMenuComponent implements OnInit, OnDestroy {
    foodInMenus: IFoodInMenu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private foodInMenuService: FoodInMenuService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.foodInMenuService.query().subscribe(
            (res: HttpResponse<IFoodInMenu[]>) => {
                this.foodInMenus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFoodInMenus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFoodInMenu) {
        return item.id;
    }

    registerChangeInFoodInMenus() {
        this.eventSubscriber = this.eventManager.subscribe('foodInMenuListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
