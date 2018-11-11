/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { TranslationComponent } from 'app/entities/translation/translation.component';
import { TranslationService } from 'app/entities/translation/translation.service';
import { Translation } from 'app/shared/model/translation.model';

describe('Component Tests', () => {
    describe('Translation Management Component', () => {
        let comp: TranslationComponent;
        let fixture: ComponentFixture<TranslationComponent>;
        let service: TranslationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [TranslationComponent],
                providers: []
            })
                .overrideTemplate(TranslationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TranslationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranslationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Translation(123)],
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
