/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { IngredientsToOrderUpdateComponent } from 'app/entities/ingredients-to-order/ingredients-to-order-update.component';
import { IngredientsToOrderService } from 'app/entities/ingredients-to-order/ingredients-to-order.service';
import { IngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';

describe('Component Tests', () => {
    describe('IngredientsToOrder Management Update Component', () => {
        let comp: IngredientsToOrderUpdateComponent;
        let fixture: ComponentFixture<IngredientsToOrderUpdateComponent>;
        let service: IngredientsToOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [IngredientsToOrderUpdateComponent]
            })
                .overrideTemplate(IngredientsToOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IngredientsToOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientsToOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IngredientsToOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ingredientsToOrder = entity;
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
                    const entity = new IngredientsToOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ingredientsToOrder = entity;
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
