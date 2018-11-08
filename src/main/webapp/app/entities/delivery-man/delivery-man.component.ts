import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDeliveryMan } from 'app/shared/model/delivery-man.model';
import { Principal } from 'app/core';
import { DeliveryManService } from './delivery-man.service';

@Component({
    selector: 'jhi-delivery-man',
    templateUrl: './delivery-man.component.html'
})
export class DeliveryManComponent implements OnInit, OnDestroy {
    deliveryMen: IDeliveryMan[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private deliveryManService: DeliveryManService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.deliveryManService.query().subscribe(
            (res: HttpResponse<IDeliveryMan[]>) => {
                this.deliveryMen = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDeliveryMen();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDeliveryMan) {
        return item.id;
    }

    registerChangeInDeliveryMen() {
        this.eventSubscriber = this.eventManager.subscribe('deliveryManListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
