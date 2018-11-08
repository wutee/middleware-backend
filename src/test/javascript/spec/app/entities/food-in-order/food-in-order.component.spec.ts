/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyTestTestModule } from '../../../test.module';
import { FoodInOrderComponent } from 'app/entities/food-in-order/food-in-order.component';
import { FoodInOrderService } from 'app/entities/food-in-order/food-in-order.service';
import { FoodInOrder } from 'app/shared/model/food-in-order.model';

describe('Component Tests', () => {
    describe('FoodInOrder Management Component', () => {
        let comp: FoodInOrderComponent;
        let fixture: ComponentFixture<FoodInOrderComponent>;
        let service: FoodInOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [FoodInOrderComponent],
                providers: []
            })
                .overrideTemplate(FoodInOrderComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodInOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodInOrderService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FoodInOrder(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.foodInOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
