/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { OpinieUpdateComponent } from 'app/entities/opinie/opinie-update.component';
import { OpinieService } from 'app/entities/opinie/opinie.service';
import { Opinie } from 'app/shared/model/opinie.model';

describe('Component Tests', () => {
  describe('Opinie Management Update Component', () => {
    let comp: OpinieUpdateComponent;
    let fixture: ComponentFixture<OpinieUpdateComponent>;
    let service: OpinieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [OpinieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OpinieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpinieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpinieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Opinie(123);
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
        const entity = new Opinie();
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
