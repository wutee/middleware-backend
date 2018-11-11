import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';

@Component({
    selector: 'jhi-delivery-personnel-detail',
    templateUrl: './delivery-personnel-detail.component.html'
})
export class DeliveryPersonnelDetailComponent implements OnInit {
    deliveryPersonnel: IDeliveryPersonnel;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deliveryPersonnel }) => {
            this.deliveryPersonnel = deliveryPersonnel;
        });
    }

    previousState() {
        window.history.back();
    }
}
