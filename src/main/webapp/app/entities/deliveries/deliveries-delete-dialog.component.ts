import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveries } from 'app/shared/model/deliveries.model';
import { DeliveriesService } from './deliveries.service';

@Component({
    selector: 'jhi-deliveries-delete-dialog',
    templateUrl: './deliveries-delete-dialog.component.html'
})
export class DeliveriesDeleteDialogComponent {
    deliveries: IDeliveries;

    constructor(private deliveriesService: DeliveriesService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deliveriesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'deliveriesListModification',
                content: 'Deleted an deliveries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deliveries-delete-popup',
    template: ''
})
export class DeliveriesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deliveries }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DeliveriesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.deliveries = deliveries;
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
