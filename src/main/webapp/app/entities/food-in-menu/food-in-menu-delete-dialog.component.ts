import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodInMenu } from 'app/shared/model/food-in-menu.model';
import { FoodInMenuService } from './food-in-menu.service';

@Component({
    selector: 'jhi-food-in-menu-delete-dialog',
    templateUrl: './food-in-menu-delete-dialog.component.html'
})
export class FoodInMenuDeleteDialogComponent {
    foodInMenu: IFoodInMenu;

    constructor(private foodInMenuService: FoodInMenuService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.foodInMenuService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'foodInMenuListModification',
                content: 'Deleted an foodInMenu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-food-in-menu-delete-popup',
    template: ''
})
export class FoodInMenuDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodInMenu }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FoodInMenuDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.foodInMenu = foodInMenu;
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
