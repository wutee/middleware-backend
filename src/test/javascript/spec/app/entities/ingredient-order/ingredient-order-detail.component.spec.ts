/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { IngredientOrderDetailComponent } from 'app/entities/ingredient-order/ingredient-order-detail.component';
import { IngredientOrder } from 'app/shared/model/ingredient-order.model';

describe('Component Tests', () => {
    describe('IngredientOrder Management Detail Component', () => {
        let comp: IngredientOrderDetailComponent;
        let fixture: ComponentFixture<IngredientOrderDetailComponent>;
        const route = ({ data: of({ ingredientOrder: new IngredientOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [IngredientOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IngredientOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IngredientOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ingredientOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
