import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';
import { DeliveryPersonnelService } from './delivery-personnel.service';

@Component({
    selector: 'jhi-delivery-personnel-update',
    templateUrl: './delivery-personnel-update.component.html'
})
export class DeliveryPersonnelUpdateComponent implements OnInit {
    deliveryPersonnel: IDeliveryPersonnel;
    isSaving: boolean;

    constructor(private deliveryPersonnelService: DeliveryPersonnelService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deliveryPersonnel }) => {
            this.deliveryPersonnel = deliveryPersonnel;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deliveryPersonnel.id !== undefined) {
            this.subscribeToSaveResponse(this.deliveryPersonnelService.update(this.deliveryPersonnel));
        } else {
            this.subscribeToSaveResponse(this.deliveryPersonnelService.create(this.deliveryPersonnel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryPersonnel>>) {
        result.subscribe((res: HttpResponse<IDeliveryPersonnel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
