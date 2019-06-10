import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEscapeRoom } from 'app/shared/model/escape-room.model';
import { EscapeRoomService } from './escape-room.service';

@Component({
  selector: 'jhi-escape-room-delete-dialog',
  templateUrl: './escape-room-delete-dialog.component.html'
})
export class EscapeRoomDeleteDialogComponent {
  escapeRoom: IEscapeRoom;

  constructor(
    protected escapeRoomService: EscapeRoomService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.escapeRoomService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'escapeRoomListModification',
        content: 'Deleted an escapeRoom'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-escape-room-delete-popup',
  template: ''
})
export class EscapeRoomDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ escapeRoom }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EscapeRoomDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.escapeRoom = escapeRoom;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/escape-room', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/escape-room', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
