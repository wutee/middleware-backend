/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyTestTestModule } from '../../../test.module';
import { DeliveriesComponent } from 'app/entities/deliveries/deliveries.component';
import { DeliveriesService } from 'app/entities/deliveries/deliveries.service';
import { Deliveries } from 'app/shared/model/deliveries.model';

describe('Component Tests', () => {
    describe('Deliveries Management Component', () => {
        let comp: DeliveriesComponent;
        let fixture: ComponentFixture<DeliveriesComponent>;
        let service: DeliveriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [DeliveriesComponent],
                providers: []
            })
                .overrideTemplate(DeliveriesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeliveriesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveriesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Deliveries(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.deliveries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
