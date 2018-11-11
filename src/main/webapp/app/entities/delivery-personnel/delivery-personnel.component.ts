import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';
import { Principal } from 'app/core';
import { DeliveryPersonnelService } from './delivery-personnel.service';

@Component({
    selector: 'jhi-delivery-personnel',
    templateUrl: './delivery-personnel.component.html'
})
export class DeliveryPersonnelComponent implements OnInit, OnDestroy {
    deliveryPersonnels: IDeliveryPersonnel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private deliveryPersonnelService: DeliveryPersonnelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.deliveryPersonnelService.query().subscribe(
            (res: HttpResponse<IDeliveryPersonnel[]>) => {
                this.deliveryPersonnels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDeliveryPersonnels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDeliveryPersonnel) {
        return item.id;
    }

    registerChangeInDeliveryPersonnels() {
        this.eventSubscriber = this.eventManager.subscribe('deliveryPersonnelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
