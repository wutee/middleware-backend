import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryMan } from 'app/shared/model/delivery-man.model';
import { DeliveryManService } from './delivery-man.service';

@Component({
    selector: 'jhi-delivery-man-delete-dialog',
    templateUrl: './delivery-man-delete-dialog.component.html'
})
export class DeliveryManDeleteDialogComponent {
    deliveryMan: IDeliveryMan;

    constructor(
        private deliveryManService: DeliveryManService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deliveryManService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'deliveryManListModification',
                content: 'Deleted an deliveryMan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-delivery-man-delete-popup',
    template: ''
})
export class DeliveryManDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deliveryMan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DeliveryManDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.deliveryMan = deliveryMan;
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
