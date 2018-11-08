import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITranslations } from 'app/shared/model/translations.model';
import { TranslationsService } from './translations.service';

@Component({
    selector: 'jhi-translations-delete-dialog',
    templateUrl: './translations-delete-dialog.component.html'
})
export class TranslationsDeleteDialogComponent {
    translations: ITranslations;

    constructor(
        private translationsService: TranslationsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.translationsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'translationsListModification',
                content: 'Deleted an translations'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-translations-delete-popup',
    template: ''
})
export class TranslationsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ translations }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TranslationsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.translations = translations;
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
