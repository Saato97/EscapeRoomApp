/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { KlientUpdateComponent } from 'app/entities/klient/klient-update.component';
import { KlientService } from 'app/entities/klient/klient.service';
import { Klient } from 'app/shared/model/klient.model';

describe('Component Tests', () => {
  describe('Klient Management Update Component', () => {
    let comp: KlientUpdateComponent;
    let fixture: ComponentFixture<KlientUpdateComponent>;
    let service: KlientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [KlientUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(KlientUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KlientUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KlientService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Klient(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Klient();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
