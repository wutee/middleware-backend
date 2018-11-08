/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { TranslationsUpdateComponent } from 'app/entities/translations/translations-update.component';
import { TranslationsService } from 'app/entities/translations/translations.service';
import { Translations } from 'app/shared/model/translations.model';

describe('Component Tests', () => {
    describe('Translations Management Update Component', () => {
        let comp: TranslationsUpdateComponent;
        let fixture: ComponentFixture<TranslationsUpdateComponent>;
        let service: TranslationsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [TranslationsUpdateComponent]
            })
                .overrideTemplate(TranslationsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TranslationsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranslationsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Translations(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.translations = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Translations();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.translations = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
