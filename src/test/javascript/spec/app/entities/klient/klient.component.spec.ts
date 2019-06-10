/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { KlientComponent } from 'app/entities/klient/klient.component';
import { KlientService } from 'app/entities/klient/klient.service';
import { Klient } from 'app/shared/model/klient.model';

describe('Component Tests', () => {
  describe('Klient Management Component', () => {
    let comp: KlientComponent;
    let fixture: ComponentFixture<KlientComponent>;
    let service: KlientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [KlientComponent],
        providers: []
      })
        .overrideTemplate(KlientComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KlientComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KlientService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Klient(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.klients[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
