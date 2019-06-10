import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWlasciciel } from 'app/shared/model/wlasciciel.model';
import { AccountService } from 'app/core';
import { WlascicielService } from './wlasciciel.service';

@Component({
  selector: 'jhi-wlasciciel',
  templateUrl: './wlasciciel.component.html'
})
export class WlascicielComponent implements OnInit, OnDestroy {
  wlasciciels: IWlasciciel[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected wlascicielService: WlascicielService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.wlascicielService
      .query()
      .pipe(
        filter((res: HttpResponse<IWlasciciel[]>) => res.ok),
        map((res: HttpResponse<IWlasciciel[]>) => res.body)
      )
      .subscribe(
        (res: IWlasciciel[]) => {
          this.wlasciciels = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInWlasciciels();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWlasciciel) {
    return item.id;
  }

  registerChangeInWlasciciels() {
    this.eventSubscriber = this.eventManager.subscribe('wlascicielListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
