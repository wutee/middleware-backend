/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropsyTestTestModule } from '../../../test.module';
import { FoodInMenuDeleteDialogComponent } from 'app/entities/food-in-menu/food-in-menu-delete-dialog.component';
import { FoodInMenuService } from 'app/entities/food-in-menu/food-in-menu.service';

describe('Component Tests', () => {
    describe('FoodInMenu Management Delete Component', () => {
        let comp: FoodInMenuDeleteDialogComponent;
        let fixture: ComponentFixture<FoodInMenuDeleteDialogComponent>;
        let service: FoodInMenuService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [FoodInMenuDeleteDialogComponent]
            })
                .overrideTemplate(FoodInMenuDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FoodInMenuDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodInMenuService);
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
