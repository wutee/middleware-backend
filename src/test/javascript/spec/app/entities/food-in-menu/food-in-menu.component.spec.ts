/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyTestTestModule } from '../../../test.module';
import { FoodInMenuComponent } from 'app/entities/food-in-menu/food-in-menu.component';
import { FoodInMenuService } from 'app/entities/food-in-menu/food-in-menu.service';
import { FoodInMenu } from 'app/shared/model/food-in-menu.model';

describe('Component Tests', () => {
    describe('FoodInMenu Management Component', () => {
        let comp: FoodInMenuComponent;
        let fixture: ComponentFixture<FoodInMenuComponent>;
        let service: FoodInMenuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [FoodInMenuComponent],
                providers: []
            })
                .overrideTemplate(FoodInMenuComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodInMenuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodInMenuService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FoodInMenu(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.foodInMenus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
