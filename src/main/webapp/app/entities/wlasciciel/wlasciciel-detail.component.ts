import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWlasciciel } from 'app/shared/model/wlasciciel.model';

@Component({
  selector: 'jhi-wlasciciel-detail',
  templateUrl: './wlasciciel-detail.component.html'
})
export class WlascicielDetailComponent implements OnInit {
  wlasciciel: IWlasciciel;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wlasciciel }) => {
      this.wlasciciel = wlasciciel;
    });
  }

  previousState() {
    window.history.back();
  }
}
