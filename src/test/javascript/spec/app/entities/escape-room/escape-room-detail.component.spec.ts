/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { EscapeRoomDetailComponent } from 'app/entities/escape-room/escape-room-detail.component';
import { EscapeRoom } from 'app/shared/model/escape-room.model';

describe('Component Tests', () => {
  describe('EscapeRoom Management Detail Component', () => {
    let comp: EscapeRoomDetailComponent;
    let fixture: ComponentFixture<EscapeRoomDetailComponent>;
    const route = ({ data: of({ escapeRoom: new EscapeRoom(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [EscapeRoomDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EscapeRoomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EscapeRoomDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.escapeRoom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
