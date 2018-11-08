/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyTestTestModule } from '../../../test.module';
import { DeliveriesDeleteDialogComponent } from 'app/entities/deliveries/deliveries-delete-dialog.component';
import { DeliveriesService } from 'app/entities/deliveries/deliveries.service';

describe('Component Tests', () => {
    describe('Deliveries Management Delete Component', () => {
        let comp: DeliveriesDeleteDialogComponent;
        let fixture: ComponentFixture<DeliveriesDeleteDialogComponent>;
        let service: DeliveriesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [DeliveriesDeleteDialogComponent]
            })
                .overrideTemplate(DeliveriesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeliveriesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveriesService);
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
