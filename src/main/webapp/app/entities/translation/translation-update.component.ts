import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITranslation } from 'app/shared/model/translation.model';
import { TranslationService } from './translation.service';
import { IFood } from 'app/shared/model/food.model';
import { FoodService } from 'app/entities/food';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language';

@Component({
    selector: 'jhi-translation-update',
    templateUrl: './translation-update.component.html'
})
export class TranslationUpdateComponent implements OnInit {
    translation: ITranslation;
    isSaving: boolean;

    foods: IFood[];

    languages: ILanguage[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private translationService: TranslationService,
        private foodService: FoodService,
        private languageService: LanguageService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ translation }) => {
            this.translation = translation;
        });
        this.foodService.query({ filter: 'translation-is-null' }).subscribe(
            (res: HttpResponse<IFood[]>) => {
                if (!this.translation.food || !this.translation.food.id) {
                    this.foods = res.body;
                } else {
                    this.foodService.find(this.translation.food.id).subscribe(
                        (subRes: HttpResponse<IFood>) => {
                            this.foods = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.languageService.query().subscribe(
            (res: HttpResponse<ILanguage[]>) => {
                this.languages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.translation.id !== undefined) {
            this.subscribeToSaveResponse(this.translationService.update(this.translation));
        } else {
            this.subscribeToSaveResponse(this.translationService.create(this.translation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITranslation>>) {
        result.subscribe((res: HttpResponse<ITranslation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFoodById(index: number, item: IFood) {
        return item.id;
    }

    trackLanguageById(index: number, item: ILanguage) {
        return item.id;
    }
}
