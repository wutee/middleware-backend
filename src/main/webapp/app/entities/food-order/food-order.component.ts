import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFoodOrder } from 'app/shared/model/food-order.model';
import { Principal } from 'app/core';
import { FoodOrderService } from './food-order.service';

@Component({
    selector: 'jhi-food-order',
    templateUrl: './food-order.component.html'
})
export class FoodOrderComponent implements OnInit, OnDestroy {
    foodOrders: IFoodOrder[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private foodOrderService: FoodOrderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.foodOrderService.query().subscribe(
            (res: HttpResponse<IFoodOrder[]>) => {
                this.foodOrders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFoodOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFoodOrder) {
        return item.id;
    }

    registerChangeInFoodOrders() {
        this.eventSubscriber = this.eventManager.subscribe('foodOrderListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
