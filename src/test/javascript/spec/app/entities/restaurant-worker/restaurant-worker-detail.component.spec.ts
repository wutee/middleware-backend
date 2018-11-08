/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { RestaurantWorkerDetailComponent } from 'app/entities/restaurant-worker/restaurant-worker-detail.component';
import { RestaurantWorker } from 'app/shared/model/restaurant-worker.model';

describe('Component Tests', () => {
    describe('RestaurantWorker Management Detail Component', () => {
        let comp: RestaurantWorkerDetailComponent;
        let fixture: ComponentFixture<RestaurantWorkerDetailComponent>;
        const route = ({ data: of({ restaurantWorker: new RestaurantWorker(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [RestaurantWorkerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RestaurantWorkerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RestaurantWorkerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.restaurantWorker).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
