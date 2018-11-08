/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { IngredientsToOrderDetailComponent } from 'app/entities/ingredients-to-order/ingredients-to-order-detail.component';
import { IngredientsToOrder } from 'app/shared/model/ingredients-to-order.model';

describe('Component Tests', () => {
    describe('IngredientsToOrder Management Detail Component', () => {
        let comp: IngredientsToOrderDetailComponent;
        let fixture: ComponentFixture<IngredientsToOrderDetailComponent>;
        const route = ({ data: of({ ingredientsToOrder: new IngredientsToOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [IngredientsToOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IngredientsToOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IngredientsToOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ingredientsToOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
