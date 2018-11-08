import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFoodInOrder } from 'app/shared/model/food-in-order.model';
import { Principal } from 'app/core';
import { FoodInOrderService } from './food-in-order.service';

@Component({
    selector: 'jhi-food-in-order',
    templateUrl: './food-in-order.component.html'
})
export class FoodInOrderComponent implements OnInit, OnDestroy {
    foodInOrders: IFoodInOrder[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private foodInOrderService: FoodInOrderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.foodInOrderService.query().subscribe(
            (res: HttpResponse<IFoodInOrder[]>) => {
                this.foodInOrders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFoodInOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFoodInOrder) {
        return item.id;
    }

    registerChangeInFoodInOrders() {
        this.eventSubscriber = this.eventManager.subscribe('foodInOrderListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
