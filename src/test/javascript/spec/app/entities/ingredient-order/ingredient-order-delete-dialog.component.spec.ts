/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyTestTestModule } from '../../../test.module';
import { IngredientOrderDeleteDialogComponent } from 'app/entities/ingredient-order/ingredient-order-delete-dialog.component';
import { IngredientOrderService } from 'app/entities/ingredient-order/ingredient-order.service';

describe('Component Tests', () => {
    describe('IngredientOrder Management Delete Component', () => {
        let comp: IngredientOrderDeleteDialogComponent;
        let fixture: ComponentFixture<IngredientOrderDeleteDialogComponent>;
        let service: IngredientOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [IngredientOrderDeleteDialogComponent]
            })
                .overrideTemplate(IngredientOrderDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IngredientOrderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientOrderService);
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
