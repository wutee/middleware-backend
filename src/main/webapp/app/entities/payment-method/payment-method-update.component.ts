import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPaymentMethod } from 'app/shared/model/payment-method.model';
import { PaymentMethodService } from './payment-method.service';
import { IPayment } from 'app/shared/model/payment.model';
import { PaymentService } from 'app/entities/payment';

@Component({
    selector: 'jhi-payment-method-update',
    templateUrl: './payment-method-update.component.html'
})
export class PaymentMethodUpdateComponent implements OnInit {
    paymentMethod: IPaymentMethod;
    isSaving: boolean;

    payments: IPayment[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private paymentMethodService: PaymentMethodService,
        private paymentService: PaymentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentMethod }) => {
            this.paymentMethod = paymentMethod;
        });
        this.paymentService.query().subscribe(
            (res: HttpResponse<IPayment[]>) => {
                this.payments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.paymentMethod.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentMethodService.update(this.paymentMethod));
        } else {
            this.subscribeToSaveResponse(this.paymentMethodService.create(this.paymentMethod));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethod>>) {
        result.subscribe((res: HttpResponse<IPaymentMethod>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPaymentById(index: number, item: IPayment) {
        return item.id;
    }
}
