/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyTestTestModule } from '../../../test.module';
import { IngredientsToOrderDeleteDialogComponent } from 'app/entities/ingredients-to-order/ingredients-to-order-delete-dialog.component';
import { IngredientsToOrderService } from 'app/entities/ingredients-to-order/ingredients-to-order.service';

describe('Component Tests', () => {
    describe('IngredientsToOrder Management Delete Component', () => {
        let comp: IngredientsToOrderDeleteDialogComponent;
        let fixture: ComponentFixture<IngredientsToOrderDeleteDialogComponent>;
        let service: IngredientsToOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [IngredientsToOrderDeleteDialogComponent]
            })
                .overrideTemplate(IngredientsToOrderDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IngredientsToOrderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientsToOrderService);
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
