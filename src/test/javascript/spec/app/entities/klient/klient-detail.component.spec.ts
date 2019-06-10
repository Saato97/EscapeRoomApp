/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { KlientDetailComponent } from 'app/entities/klient/klient-detail.component';
import { Klient } from 'app/shared/model/klient.model';

describe('Component Tests', () => {
  describe('Klient Management Detail Component', () => {
    let comp: KlientDetailComponent;
    let fixture: ComponentFixture<KlientDetailComponent>;
    const route = ({ data: of({ klient: new Klient(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [KlientDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(KlientDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KlientDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.klient).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
