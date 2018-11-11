/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { IngredientOrderUpdateComponent } from 'app/entities/ingredient-order/ingredient-order-update.component';
import { IngredientOrderService } from 'app/entities/ingredient-order/ingredient-order.service';
import { IngredientOrder } from 'app/shared/model/ingredient-order.model';

describe('Component Tests', () => {
    describe('IngredientOrder Management Update Component', () => {
        let comp: IngredientOrderUpdateComponent;
        let fixture: ComponentFixture<IngredientOrderUpdateComponent>;
        let service: IngredientOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [IngredientOrderUpdateComponent]
            })
                .overrideTemplate(IngredientOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IngredientOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IngredientOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ingredientOrder = entity;
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
                    const entity = new IngredientOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ingredientOrder = entity;
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
