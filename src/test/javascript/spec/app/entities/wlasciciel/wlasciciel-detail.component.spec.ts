/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { WlascicielDetailComponent } from 'app/entities/wlasciciel/wlasciciel-detail.component';
import { Wlasciciel } from 'app/shared/model/wlasciciel.model';

describe('Component Tests', () => {
  describe('Wlasciciel Management Detail Component', () => {
    let comp: WlascicielDetailComponent;
    let fixture: ComponentFixture<WlascicielDetailComponent>;
    const route = ({ data: of({ wlasciciel: new Wlasciciel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [WlascicielDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WlascicielDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WlascicielDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.wlasciciel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
