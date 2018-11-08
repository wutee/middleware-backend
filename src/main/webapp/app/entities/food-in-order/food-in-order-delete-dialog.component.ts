import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodInOrder } from 'app/shared/model/food-in-order.model';
import { FoodInOrderService } from './food-in-order.service';

@Component({
    selector: 'jhi-food-in-order-delete-dialog',
    templateUrl: './food-in-order-delete-dialog.component.html'
})
export class FoodInOrderDeleteDialogComponent {
    foodInOrder: IFoodInOrder;

    constructor(
        private foodInOrderService: FoodInOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.foodInOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'foodInOrderListModification',
                content: 'Deleted an foodInOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-food-in-order-delete-popup',
    template: ''
})
export class FoodInOrderDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodInOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FoodInOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.foodInOrder = foodInOrder;
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
