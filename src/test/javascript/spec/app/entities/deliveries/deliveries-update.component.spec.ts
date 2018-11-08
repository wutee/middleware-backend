/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { DeliveriesUpdateComponent } from 'app/entities/deliveries/deliveries-update.component';
import { DeliveriesService } from 'app/entities/deliveries/deliveries.service';
import { Deliveries } from 'app/shared/model/deliveries.model';

describe('Component Tests', () => {
    describe('Deliveries Management Update Component', () => {
        let comp: DeliveriesUpdateComponent;
        let fixture: ComponentFixture<DeliveriesUpdateComponent>;
        let service: DeliveriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [DeliveriesUpdateComponent]
            })
                .overrideTemplate(DeliveriesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeliveriesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveriesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Deliveries(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deliveries = entity;
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
                    const entity = new Deliveries();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deliveries = entity;
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
