/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { OpinieDetailComponent } from 'app/entities/opinie/opinie-detail.component';
import { Opinie } from 'app/shared/model/opinie.model';

describe('Component Tests', () => {
  describe('Opinie Management Detail Component', () => {
    let comp: OpinieDetailComponent;
    let fixture: ComponentFixture<OpinieDetailComponent>;
    const route = ({ data: of({ opinie: new Opinie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [OpinieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OpinieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OpinieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.opinie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
