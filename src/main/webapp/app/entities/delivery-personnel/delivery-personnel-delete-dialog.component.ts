import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';
import { DeliveryPersonnelService } from './delivery-personnel.service';

@Component({
    selector: 'jhi-delivery-personnel-delete-dialog',
    templateUrl: './delivery-personnel-delete-dialog.component.html'
})
export class DeliveryPersonnelDeleteDialogComponent {
    deliveryPersonnel: IDeliveryPersonnel;

    constructor(
        private deliveryPersonnelService: DeliveryPersonnelService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deliveryPersonnelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'deliveryPersonnelListModification',
                content: 'Deleted an deliveryPersonnel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-delivery-personnel-delete-popup',
    template: ''
})
export class DeliveryPersonnelDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deliveryPersonnel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DeliveryPersonnelDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.deliveryPersonnel = deliveryPersonnel;
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
