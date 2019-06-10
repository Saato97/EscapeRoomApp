import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IWlasciciel, Wlasciciel } from 'app/shared/model/wlasciciel.model';
import { WlascicielService } from './wlasciciel.service';
import { IOsoba } from 'app/shared/model/osoba.model';
import { OsobaService } from 'app/entities/osoba';
import { IEscapeRoom } from 'app/shared/model/escape-room.model';
import { EscapeRoomService } from 'app/entities/escape-room';

@Component({
  selector: 'jhi-wlasciciel-update',
  templateUrl: './wlasciciel-update.component.html'
})
export class WlascicielUpdateComponent implements OnInit {
  wlasciciel: IWlasciciel;
  isSaving: boolean;

  osobas: IOsoba[];

  escaperooms: IEscapeRoom[];

  editForm = this.fb.group({
    id: [],
    osoba: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected wlascicielService: WlascicielService,
    protected osobaService: OsobaService,
    protected escapeRoomService: EscapeRoomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ wlasciciel }) => {
      this.updateForm(wlasciciel);
      this.wlasciciel = wlasciciel;
    });
    this.osobaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOsoba[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOsoba[]>) => response.body)
      )
      .subscribe((res: IOsoba[]) => (this.osobas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.escapeRoomService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEscapeRoom[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEscapeRoom[]>) => response.body)
      )
      .subscribe((res: IEscapeRoom[]) => (this.escaperooms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(wlasciciel: IWlasciciel) {
    this.editForm.patchValue({
      id: wlasciciel.id,
      osoba: wlasciciel.osoba
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const wlasciciel = this.createFromForm();
    if (wlasciciel.id !== undefined) {
      this.subscribeToSaveResponse(this.wlascicielService.update(wlasciciel));
    } else {
      this.subscribeToSaveResponse(this.wlascicielService.create(wlasciciel));
    }
  }

  private createFromForm(): IWlasciciel {
    const entity = {
      ...new Wlasciciel(),
      id: this.editForm.get(['id']).value,
      osoba: this.editForm.get(['osoba']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWlasciciel>>) {
    result.subscribe((res: HttpResponse<IWlasciciel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackOsobaById(index: number, item: IOsoba) {
    return item.id;
  }

  trackEscapeRoomById(index: number, item: IEscapeRoom) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
