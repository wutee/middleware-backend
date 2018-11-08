import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITranslations } from 'app/shared/model/translations.model';
import { Principal } from 'app/core';
import { TranslationsService } from './translations.service';

@Component({
    selector: 'jhi-translations',
    templateUrl: './translations.component.html'
})
export class TranslationsComponent implements OnInit, OnDestroy {
    translations: ITranslations[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private translationsService: TranslationsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.translationsService.query().subscribe(
            (res: HttpResponse<ITranslations[]>) => {
                this.translations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTranslations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITranslations) {
        return item.id;
    }

    registerChangeInTranslations() {
        this.eventSubscriber = this.eventManager.subscribe('translationsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
