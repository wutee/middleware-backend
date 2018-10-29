/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyBackendTestModule } from '../../../test.module';
import { RestaurantWorkerDeleteDialogComponent } from 'app/entities/restaurant-worker/restaurant-worker-delete-dialog.component';
import { RestaurantWorkerService } from 'app/entities/restaurant-worker/restaurant-worker.service';

describe('Component Tests', () => {
    describe('RestaurantWorker Management Delete Component', () => {
        let comp: RestaurantWorkerDeleteDialogComponent;
        let fixture: ComponentFixture<RestaurantWorkerDeleteDialogComponent>;
        let service: RestaurantWorkerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendTestModule],
                declarations: [RestaurantWorkerDeleteDialogComponent]
            })
                .overrideTemplate(RestaurantWorkerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RestaurantWorkerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantWorkerService);
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
