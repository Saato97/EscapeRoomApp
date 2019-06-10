import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWizyty } from 'app/shared/model/wizyty.model';

@Component({
  selector: 'jhi-wizyty-detail',
  templateUrl: './wizyty-detail.component.html'
})
export class WizytyDetailComponent implements OnInit {
  wizyty: IWizyty;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wizyty }) => {
      this.wizyty = wizyty;
    });
  }

  previousState() {
    window.history.back();
  }
}
