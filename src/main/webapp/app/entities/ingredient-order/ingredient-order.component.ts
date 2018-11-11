import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIngredientOrder } from 'app/shared/model/ingredient-order.model';
import { Principal } from 'app/core';
import { IngredientOrderService } from './ingredient-order.service';

@Component({
    selector: 'jhi-ingredient-order',
    templateUrl: './ingredient-order.component.html'
})
export class IngredientOrderComponent implements OnInit, OnDestroy {
    ingredientOrders: IIngredientOrder[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ingredientOrderService: IngredientOrderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.ingredientOrderService.query().subscribe(
            (res: HttpResponse<IIngredientOrder[]>) => {
                this.ingredientOrders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIngredientOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIngredientOrder) {
        return item.id;
    }

    registerChangeInIngredientOrders() {
        this.eventSubscriber = this.eventManager.subscribe('ingredientOrderListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
