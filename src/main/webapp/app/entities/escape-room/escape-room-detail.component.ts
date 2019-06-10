import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEscapeRoom } from 'app/shared/model/escape-room.model';

@Component({
  selector: 'jhi-escape-room-detail',
  templateUrl: './escape-room-detail.component.html'
})
export class EscapeRoomDetailComponent implements OnInit {
  escapeRoom: IEscapeRoom;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ escapeRoom }) => {
      this.escapeRoom = escapeRoom;
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
