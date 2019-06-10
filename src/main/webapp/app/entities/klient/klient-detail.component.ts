import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKlient } from 'app/shared/model/klient.model';

@Component({
  selector: 'jhi-klient-detail',
  templateUrl: './klient-detail.component.html'
})
export class KlientDetailComponent implements OnInit {
  klient: IKlient;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ klient }) => {
      this.klient = klient;
    });
  }

  previousState() {
    window.history.back();
  }
}
