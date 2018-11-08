/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { FoodInMenuDetailComponent } from 'app/entities/food-in-menu/food-in-menu-detail.component';
import { FoodInMenu } from 'app/shared/model/food-in-menu.model';

describe('Component Tests', () => {
    describe('FoodInMenu Management Detail Component', () => {
        let comp: FoodInMenuDetailComponent;
        let fixture: ComponentFixture<FoodInMenuDetailComponent>;
        const route = ({ data: of({ foodInMenu: new FoodInMenu(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [FoodInMenuDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FoodInMenuDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FoodInMenuDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.foodInMenu).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
