/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { WlascicielUpdateComponent } from 'app/entities/wlasciciel/wlasciciel-update.component';
import { WlascicielService } from 'app/entities/wlasciciel/wlasciciel.service';
import { Wlasciciel } from 'app/shared/model/wlasciciel.model';

describe('Component Tests', () => {
  describe('Wlasciciel Management Update Component', () => {
    let comp: WlascicielUpdateComponent;
    let fixture: ComponentFixture<WlascicielUpdateComponent>;
    let service: WlascicielService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [WlascicielUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WlascicielUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WlascicielUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WlascicielService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Wlasciciel(123);
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
        const entity = new Wlasciciel();
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
