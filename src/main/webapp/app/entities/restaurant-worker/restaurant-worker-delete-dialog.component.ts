import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRestaurantWorker } from 'app/shared/model/restaurant-worker.model';
import { RestaurantWorkerService } from './restaurant-worker.service';

@Component({
    selector: 'jhi-restaurant-worker-delete-dialog',
    templateUrl: './restaurant-worker-delete-dialog.component.html'
})
export class RestaurantWorkerDeleteDialogComponent {
    restaurantWorker: IRestaurantWorker;

    constructor(
        private restaurantWorkerService: RestaurantWorkerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.restaurantWorkerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'restaurantWorkerListModification',
                content: 'Deleted an restaurantWorker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-restaurant-worker-delete-popup',
    template: ''
})
export class RestaurantWorkerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ restaurantWorker }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RestaurantWorkerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.restaurantWorker = restaurantWorker;
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
