/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { WlascicielComponent } from 'app/entities/wlasciciel/wlasciciel.component';
import { WlascicielService } from 'app/entities/wlasciciel/wlasciciel.service';
import { Wlasciciel } from 'app/shared/model/wlasciciel.model';

describe('Component Tests', () => {
  describe('Wlasciciel Management Component', () => {
    let comp: WlascicielComponent;
    let fixture: ComponentFixture<WlascicielComponent>;
    let service: WlascicielService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [WlascicielComponent],
        providers: []
      })
        .overrideTemplate(WlascicielComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WlascicielComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WlascicielService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Wlasciciel(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.wlasciciels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
