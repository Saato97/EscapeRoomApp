import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOpinie } from 'app/shared/model/opinie.model';

@Component({
  selector: 'jhi-opinie-detail',
  templateUrl: './opinie-detail.component.html'
})
export class OpinieDetailComponent implements OnInit {
  opinie: IOpinie;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ opinie }) => {
      this.opinie = opinie;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
