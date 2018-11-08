import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';
import { IngredientsToOrderService } from './ingredients-to-order.service';

@Component({
    selector: 'jhi-ingredients-to-order-delete-dialog',
    templateUrl: './ingredients-to-order-delete-dialog.component.html'
})
export class IngredientsToOrderDeleteDialogComponent {
    ingredientsToOrder: IIngredientsToOrder;

    constructor(
        private ingredientsToOrderService: IngredientsToOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ingredientsToOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ingredientsToOrderListModification',
                content: 'Deleted an ingredientsToOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ingredients-to-order-delete-popup',
    template: ''
})
export class IngredientsToOrderDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ingredientsToOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IngredientsToOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.ingredientsToOrder = ingredientsToOrder;
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
