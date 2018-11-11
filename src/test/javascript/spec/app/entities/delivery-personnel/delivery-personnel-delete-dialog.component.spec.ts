/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { DeliveryPersonnelDeleteDialogComponent } from 'app/entities/delivery-personnel/delivery-personnel-delete-dialog.component';
import { DeliveryPersonnelService } from 'app/entities/delivery-personnel/delivery-personnel.service';

describe('Component Tests', () => {
    describe('DeliveryPersonnel Management Delete Component', () => {
        let comp: DeliveryPersonnelDeleteDialogComponent;
        let fixture: ComponentFixture<DeliveryPersonnelDeleteDialogComponent>;
        let service: DeliveryPersonnelService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [DeliveryPersonnelDeleteDialogComponent]
            })
                .overrideTemplate(DeliveryPersonnelDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeliveryPersonnelDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveryPersonnelService);
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
