import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITranslation } from 'app/shared/model/translation.model';

@Component({
    selector: 'jhi-translation-detail',
    templateUrl: './translation-detail.component.html'
})
export class TranslationDetailComponent implements OnInit {
    translation: ITranslation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ translation }) => {
            this.translation = translation;
        });
    }

    previousState() {
        window.history.back();
    }
}
