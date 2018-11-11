import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngredientOrder } from 'app/shared/model/ingredient-order.model';
import { IngredientOrderService } from './ingredient-order.service';

@Component({
    selector: 'jhi-ingredient-order-delete-dialog',
    templateUrl: './ingredient-order-delete-dialog.component.html'
})
export class IngredientOrderDeleteDialogComponent {
    ingredientOrder: IIngredientOrder;

    constructor(
        private ingredientOrderService: IngredientOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ingredientOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ingredientOrderListModification',
                content: 'Deleted an ingredientOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ingredient-order-delete-popup',
    template: ''
})
export class IngredientOrderDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ingredientOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IngredientOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.ingredientOrder = ingredientOrder;
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
