import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITranslations } from 'app/shared/model/translations.model';

@Component({
    selector: 'jhi-translations-detail',
    templateUrl: './translations-detail.component.html'
})
export class TranslationsDetailComponent implements OnInit {
    translations: ITranslations;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ translations }) => {
            this.translations = translations;
        });
    }

    previousState() {
        window.history.back();
    }
}
