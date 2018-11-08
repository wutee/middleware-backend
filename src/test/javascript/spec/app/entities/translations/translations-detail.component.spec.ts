/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { TranslationsDetailComponent } from 'app/entities/translations/translations-detail.component';
import { Translations } from 'app/shared/model/translations.model';

describe('Component Tests', () => {
    describe('Translations Management Detail Component', () => {
        let comp: TranslationsDetailComponent;
        let fixture: ComponentFixture<TranslationsDetailComponent>;
        const route = ({ data: of({ translations: new Translations(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [TranslationsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TranslationsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TranslationsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.translations).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
