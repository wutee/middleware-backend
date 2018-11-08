import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeliveries } from 'app/shared/model/deliveries.model';

@Component({
    selector: 'jhi-deliveries-detail',
    templateUrl: './deliveries-detail.component.html'
})
export class DeliveriesDetailComponent implements OnInit {
    deliveries: IDeliveries;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deliveries }) => {
            this.deliveries = deliveries;
        });
    }

    previousState() {
        window.history.back();
    }
}
