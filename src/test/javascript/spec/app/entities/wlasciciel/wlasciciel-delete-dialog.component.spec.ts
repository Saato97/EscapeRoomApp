/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { WlascicielDeleteDialogComponent } from 'app/entities/wlasciciel/wlasciciel-delete-dialog.component';
import { WlascicielService } from 'app/entities/wlasciciel/wlasciciel.service';

describe('Component Tests', () => {
  describe('Wlasciciel Management Delete Component', () => {
    let comp: WlascicielDeleteDialogComponent;
    let fixture: ComponentFixture<WlascicielDeleteDialogComponent>;
    let service: WlascicielService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [WlascicielDeleteDialogComponent]
      })
        .overrideTemplate(WlascicielDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WlascicielDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WlascicielService);
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
