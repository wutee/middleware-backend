/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { TranslationDetailComponent } from 'app/entities/translation/translation-detail.component';
import { Translation } from 'app/shared/model/translation.model';

describe('Component Tests', () => {
    describe('Translation Management Detail Component', () => {
        let comp: TranslationDetailComponent;
        let fixture: ComponentFixture<TranslationDetailComponent>;
        const route = ({ data: of({ translation: new Translation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [TranslationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TranslationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TranslationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.translation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
