/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EscapeRoomAppTestModule } from '../../../test.module';
import { WizytyDeleteDialogComponent } from 'app/entities/wizyty/wizyty-delete-dialog.component';
import { WizytyService } from 'app/entities/wizyty/wizyty.service';

describe('Component Tests', () => {
  describe('Wizyty Management Delete Component', () => {
    let comp: WizytyDeleteDialogComponent;
    let fixture: ComponentFixture<WizytyDeleteDialogComponent>;
    let service: WizytyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscapeRoomAppTestModule],
        declarations: [WizytyDeleteDialogComponent]
      })
        .overrideTemplate(WizytyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WizytyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WizytyService);
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
