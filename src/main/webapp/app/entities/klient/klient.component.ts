import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IKlient } from 'app/shared/model/klient.model';
import { AccountService } from 'app/core';
import { KlientService } from './klient.service';

@Component({
  selector: 'jhi-klient',
  templateUrl: './klient.component.html'
})
export class KlientComponent implements OnInit, OnDestroy {
  klients: IKlient[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected klientService: KlientService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.klientService
      .query()
      .pipe(
        filter((res: HttpResponse<IKlient[]>) => res.ok),
        map((res: HttpResponse<IKlient[]>) => res.body)
      )
      .subscribe(
        (res: IKlient[]) => {
          this.klients = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInKlients();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IKlient) {
    return item.id;
  }

  registerChangeInKlients() {
    this.eventSubscriber = this.eventManager.subscribe('klientListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
