import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDeliveries } from 'app/shared/model/deliveries.model';
import { DeliveriesService } from './deliveries.service';

@Component({
    selector: 'jhi-deliveries-update',
    templateUrl: './deliveries-update.component.html'
})
export class DeliveriesUpdateComponent implements OnInit {
    deliveries: IDeliveries;
    isSaving: boolean;

    constructor(private deliveriesService: DeliveriesService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deliveries }) => {
            this.deliveries = deliveries;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deliveries.id !== undefined) {
            this.subscribeToSaveResponse(this.deliveriesService.update(this.deliveries));
        } else {
            this.subscribeToSaveResponse(this.deliveriesService.create(this.deliveries));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveries>>) {
        result.subscribe((res: HttpResponse<IDeliveries>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
