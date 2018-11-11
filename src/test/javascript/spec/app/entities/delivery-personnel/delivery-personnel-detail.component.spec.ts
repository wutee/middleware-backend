/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { DeliveryPersonnelDetailComponent } from 'app/entities/delivery-personnel/delivery-personnel-detail.component';
import { DeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';

describe('Component Tests', () => {
    describe('DeliveryPersonnel Management Detail Component', () => {
        let comp: DeliveryPersonnelDetailComponent;
        let fixture: ComponentFixture<DeliveryPersonnelDetailComponent>;
        const route = ({ data: of({ deliveryPersonnel: new DeliveryPersonnel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [DeliveryPersonnelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DeliveryPersonnelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeliveryPersonnelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.deliveryPersonnel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
