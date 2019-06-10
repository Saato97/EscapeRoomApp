/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { WizytyDetailComponent } from 'app/entities/wizyty/wizyty-detail.component';
import { Wizyty } from 'app/shared/model/wizyty.model';

describe('Component Tests', () => {
  describe('Wizyty Management Detail Component', () => {
    let comp: WizytyDetailComponent;
    let fixture: ComponentFixture<WizytyDetailComponent>;
    const route = ({ data: of({ wizyty: new Wizyty(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [WizytyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WizytyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WizytyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.wizyty).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
