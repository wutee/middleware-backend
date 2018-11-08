/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { FoodInMenuUpdateComponent } from 'app/entities/food-in-menu/food-in-menu-update.component';
import { FoodInMenuService } from 'app/entities/food-in-menu/food-in-menu.service';
import { FoodInMenu } from 'app/shared/model/food-in-menu.model';

describe('Component Tests', () => {
    describe('FoodInMenu Management Update Component', () => {
        let comp: FoodInMenuUpdateComponent;
        let fixture: ComponentFixture<FoodInMenuUpdateComponent>;
        let service: FoodInMenuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [FoodInMenuUpdateComponent]
            })
                .overrideTemplate(FoodInMenuUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodInMenuUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodInMenuService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FoodInMenu(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.foodInMenu = entity;
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
                    const entity = new FoodInMenu();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.foodInMenu = entity;
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
