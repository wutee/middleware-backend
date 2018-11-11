import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from './food-order.service';

@Component({
    selector: 'jhi-food-order-delete-dialog',
    templateUrl: './food-order-delete-dialog.component.html'
})
export class FoodOrderDeleteDialogComponent {
    foodOrder: IFoodOrder;

    constructor(private foodOrderService: FoodOrderService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.foodOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'foodOrderListModification',
                content: 'Deleted an foodOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-food-order-delete-popup',
    template: ''
})
export class FoodOrderDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FoodOrderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.foodOrder = foodOrder;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
