/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { DeliveryPersonnelComponent } from 'app/entities/delivery-personnel/delivery-personnel.component';
import { DeliveryPersonnelService } from 'app/entities/delivery-personnel/delivery-personnel.service';
import { DeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';

describe('Component Tests', () => {
    describe('DeliveryPersonnel Management Component', () => {
        let comp: DeliveryPersonnelComponent;
        let fixture: ComponentFixture<DeliveryPersonnelComponent>;
        let service: DeliveryPersonnelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [DeliveryPersonnelComponent],
                providers: []
            })
                .overrideTemplate(DeliveryPersonnelComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeliveryPersonnelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveryPersonnelService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DeliveryPersonnel(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.deliveryPersonnels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
