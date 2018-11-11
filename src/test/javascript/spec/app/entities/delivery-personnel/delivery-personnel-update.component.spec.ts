/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyBackendv01TestModule } from '../../../test.module';
import { DeliveryPersonnelUpdateComponent } from 'app/entities/delivery-personnel/delivery-personnel-update.component';
import { DeliveryPersonnelService } from 'app/entities/delivery-personnel/delivery-personnel.service';
import { DeliveryPersonnel } from 'app/shared/model/delivery-personnel.model';

describe('Component Tests', () => {
    describe('DeliveryPersonnel Management Update Component', () => {
        let comp: DeliveryPersonnelUpdateComponent;
        let fixture: ComponentFixture<DeliveryPersonnelUpdateComponent>;
        let service: DeliveryPersonnelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyBackendv01TestModule],
                declarations: [DeliveryPersonnelUpdateComponent]
            })
                .overrideTemplate(DeliveryPersonnelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeliveryPersonnelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveryPersonnelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DeliveryPersonnel(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deliveryPersonnel = entity;
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
                    const entity = new DeliveryPersonnel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deliveryPersonnel = entity;
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
