import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOsoba } from 'app/shared/model/osoba.model';
import { AccountService } from 'app/core';
import { OsobaService } from './osoba.service';

@Component({
  selector: 'jhi-osoba',
  templateUrl: './osoba.component.html'
})
export class OsobaComponent implements OnInit, OnDestroy {
  osobas: IOsoba[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected osobaService: OsobaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.osobaService
      .query()
      .pipe(
        filter((res: HttpResponse<IOsoba[]>) => res.ok),
        map((res: HttpResponse<IOsoba[]>) => res.body)
      )
      .subscribe(
        (res: IOsoba[]) => {
          this.osobas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOsobas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOsoba) {
    return item.id;
  }

  registerChangeInOsobas() {
    this.eventSubscriber = this.eventManager.subscribe('osobaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
