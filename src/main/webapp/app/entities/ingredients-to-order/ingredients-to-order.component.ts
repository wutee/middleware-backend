import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';
import { Principal } from 'app/core';
import { IngredientsToOrderService } from './ingredients-to-order.service';

@Component({
    selector: 'jhi-ingredients-to-order',
    templateUrl: './ingredients-to-order.component.html'
})
export class IngredientsToOrderComponent implements OnInit, OnDestroy {
    ingredientsToOrders: IIngredientsToOrder[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ingredientsToOrderService: IngredientsToOrderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.ingredientsToOrderService.query().subscribe(
            (res: HttpResponse<IIngredientsToOrder[]>) => {
                this.ingredientsToOrders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIngredientsToOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIngredientsToOrder) {
        return item.id;
    }

    registerChangeInIngredientsToOrders() {
        this.eventSubscriber = this.eventManager.subscribe('ingredientsToOrderListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
