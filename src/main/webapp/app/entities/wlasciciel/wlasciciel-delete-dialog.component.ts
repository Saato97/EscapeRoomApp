import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWlasciciel } from 'app/shared/model/wlasciciel.model';
import { WlascicielService } from './wlasciciel.service';

@Component({
  selector: 'jhi-wlasciciel-delete-dialog',
  templateUrl: './wlasciciel-delete-dialog.component.html'
})
export class WlascicielDeleteDialogComponent {
  wlasciciel: IWlasciciel;

  constructor(
    protected wlascicielService: WlascicielService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.wlascicielService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'wlascicielListModification',
        content: 'Deleted an wlasciciel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-wlasciciel-delete-popup',
  template: ''
})
export class WlascicielDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wlasciciel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(WlascicielDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.wlasciciel = wlasciciel;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/wlasciciel', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/wlasciciel', { outlets: { popup: null } }]);
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
