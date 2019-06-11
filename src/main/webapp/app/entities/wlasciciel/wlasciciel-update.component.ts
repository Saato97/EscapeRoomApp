import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IWlasciciel, Wlasciciel } from 'app/shared/model/wlasciciel.model';
import { WlascicielService } from './wlasciciel.service';
import { IEscapeRoom } from 'app/shared/model/escape-room.model';
import { EscapeRoomService } from 'app/entities/escape-room';

@Component({
  selector: 'jhi-wlasciciel-update',
  templateUrl: './wlasciciel-update.component.html'
})
export class WlascicielUpdateComponent implements OnInit {
  wlasciciel: IWlasciciel;
  isSaving: boolean;

  escaperooms: IEscapeRoom[];

  editForm = this.fb.group({
    id: [],
    imie: [],
    nazwisko: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected wlascicielService: WlascicielService,
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
      imie: wlasciciel.imie,
      nazwisko: wlasciciel.nazwisko
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
      imie: this.editForm.get(['imie']).value,
      nazwisko: this.editForm.get(['nazwisko']).value
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
