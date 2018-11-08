/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { FoodInOrderDetailComponent } from 'app/entities/food-in-order/food-in-order-detail.component';
import { FoodInOrder } from 'app/shared/model/food-in-order.model';

describe('Component Tests', () => {
    describe('FoodInOrder Management Detail Component', () => {
        let comp: FoodInOrderDetailComponent;
        let fixture: ComponentFixture<FoodInOrderDetailComponent>;
        const route = ({ data: of({ foodInOrder: new FoodInOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [FoodInOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FoodInOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FoodInOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.foodInOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
