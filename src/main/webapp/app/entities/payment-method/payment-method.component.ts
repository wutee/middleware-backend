import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPaymentMethod } from 'app/shared/model/payment-method.model';
import { Principal } from 'app/core';
import { PaymentMethodService } from './payment-method.service';

@Component({
    selector: 'jhi-payment-method',
    templateUrl: './payment-method.component.html'
})
export class PaymentMethodComponent implements OnInit, OnDestroy {
    paymentMethods: IPaymentMethod[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paymentMethodService: PaymentMethodService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.paymentMethodService.query().subscribe(
            (res: HttpResponse<IPaymentMethod[]>) => {
                this.paymentMethods = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPaymentMethods();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPaymentMethod) {
        return item.id;
    }

    registerChangeInPaymentMethods() {
        this.eventSubscriber = this.eventManager.subscribe('paymentMethodListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
