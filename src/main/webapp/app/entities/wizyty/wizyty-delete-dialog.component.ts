import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWizyty } from 'app/shared/model/wizyty.model';
import { WizytyService } from './wizyty.service';

@Component({
  selector: 'jhi-wizyty-delete-dialog',
  templateUrl: './wizyty-delete-dialog.component.html'
})
export class WizytyDeleteDialogComponent {
  wizyty: IWizyty;

  constructor(protected wizytyService: WizytyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.wizytyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'wizytyListModification',
        content: 'Deleted an wizyty'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-wizyty-delete-popup',
  template: ''
})
export class WizytyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wizyty }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(WizytyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.wizyty = wizyty;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/wizyty', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/wizyty', { outlets: { popup: null } }]);
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
