/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { EscapeRoomUpdateComponent } from 'app/entities/escape-room/escape-room-update.component';
import { EscapeRoomService } from 'app/entities/escape-room/escape-room.service';
import { EscapeRoom } from 'app/shared/model/escape-room.model';

describe('Component Tests', () => {
  describe('EscapeRoom Management Update Component', () => {
    let comp: EscapeRoomUpdateComponent;
    let fixture: ComponentFixture<EscapeRoomUpdateComponent>;
    let service: EscapeRoomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [EscapeRoomUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EscapeRoomUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EscapeRoomUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscapeRoomService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EscapeRoom(123);
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
        const entity = new EscapeRoom();
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
