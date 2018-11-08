/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyTestTestModule } from '../../../test.module';
import { IngredientsToOrderComponent } from 'app/entities/ingredients-to-order/ingredients-to-order.component';
import { IngredientsToOrderService } from 'app/entities/ingredients-to-order/ingredients-to-order.service';
import { IngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';

describe('Component Tests', () => {
    describe('IngredientsToOrder Management Component', () => {
        let comp: IngredientsToOrderComponent;
        let fixture: ComponentFixture<IngredientsToOrderComponent>;
        let service: IngredientsToOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [IngredientsToOrderComponent],
                providers: []
            })
                .overrideTemplate(IngredientsToOrderComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IngredientsToOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientsToOrderService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new IngredientsToOrder(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ingredientsToOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
