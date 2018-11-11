/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { TranslationDeleteDialogComponent } from 'app/entities/translation/translation-delete-dialog.component';
import { TranslationService } from 'app/entities/translation/translation.service';

describe('Component Tests', () => {
    describe('Translation Management Delete Component', () => {
        let comp: TranslationDeleteDialogComponent;
        let fixture: ComponentFixture<TranslationDeleteDialogComponent>;
        let service: TranslationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [TranslationDeleteDialogComponent]
            })
                .overrideTemplate(TranslationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TranslationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranslationService);
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
