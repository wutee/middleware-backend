import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { ICustomerOrder } from 'app/shared/model/customer-order.model';
import { CustomerOrderService } from './customer-order.service';

@Component({
    selector: 'jhi-customer-order-update',
    templateUrl: './customer-order-update.component.html'
})
export class CustomerOrderUpdateComponent implements OnInit {
    customerOrder: ICustomerOrder;
    isSaving: boolean;
    dateDp: any;
    lastUpdatedDateDp: any;

    constructor(private customerOrderService: CustomerOrderService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customerOrder }) => {
            this.customerOrder = customerOrder;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.customerOrderService.update(this.customerOrder));
        } else {
            this.subscribeToSaveResponse(this.customerOrderService.create(this.customerOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerOrder>>) {
        result.subscribe((res: HttpResponse<ICustomerOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
