/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyBackendTestModule } from '../../../test.module';
import { RestaurantWorkerComponent } from 'app/entities/restaurant-worker/restaurant-worker.component';
import { RestaurantWorkerService } from 'app/entities/restaurant-worker/restaurant-worker.service';
import { RestaurantWorker } from 'app/shared/model/restaurant-worker.model';

describe('Component Tests', () => {
    describe('RestaurantWorker Management Component', () => {
        let comp: RestaurantWorkerComponent;
        let fixture: ComponentFixture<RestaurantWorkerComponent>;
        let service: RestaurantWorkerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendTestModule],
                declarations: [RestaurantWorkerComponent],
                providers: []
            })
                .overrideTemplate(RestaurantWorkerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RestaurantWorkerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantWorkerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RestaurantWorker(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.restaurantWorkers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
