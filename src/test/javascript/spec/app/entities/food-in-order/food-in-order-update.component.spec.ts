/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { FoodInOrderUpdateComponent } from 'app/entities/food-in-order/food-in-order-update.component';
import { FoodInOrderService } from 'app/entities/food-in-order/food-in-order.service';
import { FoodInOrder } from 'app/shared/model/food-in-order.model';

describe('Component Tests', () => {
    describe('FoodInOrder Management Update Component', () => {
        let comp: FoodInOrderUpdateComponent;
        let fixture: ComponentFixture<FoodInOrderUpdateComponent>;
        let service: FoodInOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [FoodInOrderUpdateComponent]
            })
                .overrideTemplate(FoodInOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodInOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodInOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FoodInOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.foodInOrder = entity;
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
                    const entity = new FoodInOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.foodInOrder = entity;
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
