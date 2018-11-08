/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyTestTestModule } from '../../../test.module';
import { TranslationsComponent } from 'app/entities/translations/translations.component';
import { TranslationsService } from 'app/entities/translations/translations.service';
import { Translations } from 'app/shared/model/translations.model';

describe('Component Tests', () => {
    describe('Translations Management Component', () => {
        let comp: TranslationsComponent;
        let fixture: ComponentFixture<TranslationsComponent>;
        let service: TranslationsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [TranslationsComponent],
                providers: []
            })
                .overrideTemplate(TranslationsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TranslationsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranslationsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Translations(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.translations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
