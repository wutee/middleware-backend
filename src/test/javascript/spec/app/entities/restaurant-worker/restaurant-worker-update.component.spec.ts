/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { RestaurantWorkerUpdateComponent } from 'app/entities/restaurant-worker/restaurant-worker-update.component';
import { RestaurantWorkerService } from 'app/entities/restaurant-worker/restaurant-worker.service';
import { RestaurantWorker } from 'app/shared/model/restaurant-worker.model';

describe('Component Tests', () => {
    describe('RestaurantWorker Management Update Component', () => {
        let comp: RestaurantWorkerUpdateComponent;
        let fixture: ComponentFixture<RestaurantWorkerUpdateComponent>;
        let service: RestaurantWorkerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [RestaurantWorkerUpdateComponent]
            })
                .overrideTemplate(RestaurantWorkerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RestaurantWorkerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantWorkerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RestaurantWorker(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.restaurantWorker = entity;
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
                    const entity = new RestaurantWorker();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.restaurantWorker = entity;
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
