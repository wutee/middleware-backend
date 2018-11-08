/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyTestTestModule } from '../../../test.module';
import { DeliveryManComponent } from 'app/entities/delivery-man/delivery-man.component';
import { DeliveryManService } from 'app/entities/delivery-man/delivery-man.service';
import { DeliveryMan } from 'app/shared/model/delivery-man.model';

describe('Component Tests', () => {
    describe('DeliveryMan Management Component', () => {
        let comp: DeliveryManComponent;
        let fixture: ComponentFixture<DeliveryManComponent>;
        let service: DeliveryManService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [DeliveryManComponent],
                providers: []
            })
                .overrideTemplate(DeliveryManComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeliveryManComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveryManService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DeliveryMan(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.deliveryMen[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
