import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDeliveryMan } from 'app/shared/model/delivery-man.model';
import { DeliveryManService } from './delivery-man.service';

@Component({
    selector: 'jhi-delivery-man-update',
    templateUrl: './delivery-man-update.component.html'
})
export class DeliveryManUpdateComponent implements OnInit {
    deliveryMan: IDeliveryMan;
    isSaving: boolean;

    constructor(private deliveryManService: DeliveryManService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deliveryMan }) => {
            this.deliveryMan = deliveryMan;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deliveryMan.id !== undefined) {
            this.subscribeToSaveResponse(this.deliveryManService.update(this.deliveryMan));
        } else {
            this.subscribeToSaveResponse(this.deliveryManService.create(this.deliveryMan));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryMan>>) {
        result.subscribe((res: HttpResponse<IDeliveryMan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
