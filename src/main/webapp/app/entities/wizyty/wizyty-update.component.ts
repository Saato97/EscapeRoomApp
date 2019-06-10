import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IWizyty, Wizyty } from 'app/shared/model/wizyty.model';
import { WizytyService } from './wizyty.service';
import { IOpinie } from 'app/shared/model/opinie.model';
import { OpinieService } from 'app/entities/opinie';
import { IKlient } from 'app/shared/model/klient.model';
import { KlientService } from 'app/entities/klient';
import { IEscapeRoom } from 'app/shared/model/escape-room.model';
import { EscapeRoomService } from 'app/entities/escape-room';

@Component({
  selector: 'jhi-wizyty-update',
  templateUrl: './wizyty-update.component.html'
})
export class WizytyUpdateComponent implements OnInit {
  wizyty: IWizyty;
  isSaving: boolean;

  opinies: IOpinie[];

  klients: IKlient[];

  escaperooms: IEscapeRoom[];
  dataWizytyDp: any;

  editForm = this.fb.group({
    id: [],
    dataWizyty: [],
    opinie: [],
    klient: [null, Validators.required],
    escaperoom: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected wizytyService: WizytyService,
    protected opinieService: OpinieService,
    protected klientService: KlientService,
    protected escapeRoomService: EscapeRoomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ wizyty }) => {
      this.updateForm(wizyty);
      this.wizyty = wizyty;
    });
    this.opinieService
      .query({ filter: 'wizyty-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IOpinie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOpinie[]>) => response.body)
      )
      .subscribe(
        (res: IOpinie[]) => {
          if (!this.wizyty.opinie || !this.wizyty.opinie.id) {
            this.opinies = res;
          } else {
            this.opinieService
              .find(this.wizyty.opinie.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IOpinie>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IOpinie>) => subResponse.body)
              )
              .subscribe(
                (subRes: IOpinie) => (this.opinies = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.klientService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IKlient[]>) => mayBeOk.ok),
        map((response: HttpResponse<IKlient[]>) => response.body)
      )
      .subscribe((res: IKlient[]) => (this.klients = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.escapeRoomService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEscapeRoom[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEscapeRoom[]>) => response.body)
      )
      .subscribe((res: IEscapeRoom[]) => (this.escaperooms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(wizyty: IWizyty) {
    this.editForm.patchValue({
      id: wizyty.id,
      dataWizyty: wizyty.dataWizyty,
      opinie: wizyty.opinie,
      klient: wizyty.klient,
      escaperoom: wizyty.escaperoom
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const wizyty = this.createFromForm();
    if (wizyty.id !== undefined) {
      this.subscribeToSaveResponse(this.wizytyService.update(wizyty));
    } else {
      this.subscribeToSaveResponse(this.wizytyService.create(wizyty));
    }
  }

  private createFromForm(): IWizyty {
    const entity = {
      ...new Wizyty(),
      id: this.editForm.get(['id']).value,
      dataWizyty: this.editForm.get(['dataWizyty']).value,
      opinie: this.editForm.get(['opinie']).value,
      klient: this.editForm.get(['klient']).value,
      escaperoom: this.editForm.get(['escaperoom']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWizyty>>) {
    result.subscribe((res: HttpResponse<IWizyty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOpinieById(index: number, item: IOpinie) {
    return item.id;
  }

  trackKlientById(index: number, item: IKlient) {
    return item.id;
  }

  trackEscapeRoomById(index: number, item: IEscapeRoom) {
    return item.id;
  }
}
