/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { IngredientOrderComponent } from 'app/entities/ingredient-order/ingredient-order.component';
import { IngredientOrderService } from 'app/entities/ingredient-order/ingredient-order.service';
import { IngredientOrder } from 'app/shared/model/ingredient-order.model';

describe('Component Tests', () => {
    describe('IngredientOrder Management Component', () => {
        let comp: IngredientOrderComponent;
        let fixture: ComponentFixture<IngredientOrderComponent>;
        let service: IngredientOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [IngredientOrderComponent],
                providers: []
            })
                .overrideTemplate(IngredientOrderComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IngredientOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientOrderService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new IngredientOrder(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ingredientOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
