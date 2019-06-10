import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOpinie } from 'app/shared/model/opinie.model';
import { OpinieService } from './opinie.service';

@Component({
  selector: 'jhi-opinie-delete-dialog',
  templateUrl: './opinie-delete-dialog.component.html'
})
export class OpinieDeleteDialogComponent {
  opinie: IOpinie;

  constructor(protected opinieService: OpinieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.opinieService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'opinieListModification',
        content: 'Deleted an opinie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-opinie-delete-popup',
  template: ''
})
export class OpinieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ opinie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OpinieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.opinie = opinie;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/opinie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/opinie', { outlets: { popup: null } }]);
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
