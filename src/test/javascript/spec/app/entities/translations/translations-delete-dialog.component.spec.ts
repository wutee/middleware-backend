/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyTestTestModule } from '../../../test.module';
import { TranslationsDeleteDialogComponent } from 'app/entities/translations/translations-delete-dialog.component';
import { TranslationsService } from 'app/entities/translations/translations.service';

describe('Component Tests', () => {
    describe('Translations Management Delete Component', () => {
        let comp: TranslationsDeleteDialogComponent;
        let fixture: ComponentFixture<TranslationsDeleteDialogComponent>;
        let service: TranslationsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [TranslationsDeleteDialogComponent]
            })
                .overrideTemplate(TranslationsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TranslationsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranslationsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
