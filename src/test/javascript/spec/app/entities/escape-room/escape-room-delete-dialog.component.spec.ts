/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { EscapeRoomDeleteDialogComponent } from 'app/entities/escape-room/escape-room-delete-dialog.component';
import { EscapeRoomService } from 'app/entities/escape-room/escape-room.service';

describe('Component Tests', () => {
  describe('EscapeRoom Management Delete Component', () => {
    let comp: EscapeRoomDeleteDialogComponent;
    let fixture: ComponentFixture<EscapeRoomDeleteDialogComponent>;
    let service: EscapeRoomService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [EscapeRoomDeleteDialogComponent]
      })
        .overrideTemplate(EscapeRoomDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EscapeRoomDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscapeRoomService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
