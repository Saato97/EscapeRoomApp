/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { KlientDeleteDialogComponent } from 'app/entities/klient/klient-delete-dialog.component';
import { KlientService } from 'app/entities/klient/klient.service';

describe('Component Tests', () => {
  describe('Klient Management Delete Component', () => {
    let comp: KlientDeleteDialogComponent;
    let fixture: ComponentFixture<KlientDeleteDialogComponent>;
    let service: KlientService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [KlientDeleteDialogComponent]
      })
        .overrideTemplate(KlientDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KlientDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KlientService);
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
