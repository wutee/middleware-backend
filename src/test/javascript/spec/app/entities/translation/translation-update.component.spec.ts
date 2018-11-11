/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { TranslationUpdateComponent } from 'app/entities/translation/translation-update.component';
import { TranslationService } from 'app/entities/translation/translation.service';
import { Translation } from 'app/shared/model/translation.model';

describe('Component Tests', () => {
    describe('Translation Management Update Component', () => {
        let comp: TranslationUpdateComponent;
        let fixture: ComponentFixture<TranslationUpdateComponent>;
        let service: TranslationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [TranslationUpdateComponent]
            })
                .overrideTemplate(TranslationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TranslationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranslationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Translation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.translation = entity;
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
                    const entity = new Translation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.translation = entity;
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
