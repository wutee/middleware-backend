/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropsyTestTestModule } from '../../../test.module';
import { DeliveriesDetailComponent } from 'app/entities/deliveries/deliveries-detail.component';
import { Deliveries } from 'app/shared/model/deliveries.model';

describe('Component Tests', () => {
    describe('Deliveries Management Detail Component', () => {
        let comp: DeliveriesDetailComponent;
        let fixture: ComponentFixture<DeliveriesDetailComponent>;
        const route = ({ data: of({ deliveries: new Deliveries(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropsyTestTestModule],
                declarations: [DeliveriesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DeliveriesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeliveriesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.deliveries).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
