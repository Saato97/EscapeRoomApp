import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKlient } from 'app/shared/model/klient.model';
import { KlientService } from './klient.service';

@Component({
  selector: 'jhi-klient-delete-dialog',
  templateUrl: './klient-delete-dialog.component.html'
})
export class KlientDeleteDialogComponent {
  klient: IKlient;

  constructor(protected klientService: KlientService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.klientService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'klientListModification',
        content: 'Deleted an klient'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-klient-delete-popup',
  template: ''
})
export class KlientDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ klient }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(KlientDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.klient = klient;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/klient', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/klient', { outlets: { popup: null } }]);
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
