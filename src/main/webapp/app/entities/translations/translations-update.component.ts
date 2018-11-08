import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITranslations } from 'app/shared/model/translations.model';
import { TranslationsService } from './translations.service';

@Component({
    selector: 'jhi-translations-update',
    templateUrl: './translations-update.component.html'
})
export class TranslationsUpdateComponent implements OnInit {
    translations: ITranslations;
    isSaving: boolean;

    constructor(private translationsService: TranslationsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ translations }) => {
            this.translations = translations;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.translations.id !== undefined) {
            this.subscribeToSaveResponse(this.translationsService.update(this.translations));
        } else {
            this.subscribeToSaveResponse(this.translationsService.create(this.translations));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITranslations>>) {
        result.subscribe((res: HttpResponse<ITranslations>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
