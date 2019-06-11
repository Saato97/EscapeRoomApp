import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEscapeRoom, EscapeRoom } from 'app/shared/model/escape-room.model';
import { EscapeRoomService } from './escape-room.service';
import { IWlasciciel } from 'app/shared/model/wlasciciel.model';
import { WlascicielService } from 'app/entities/wlasciciel';

@Component({
  selector: 'jhi-escape-room-update',
  templateUrl: './escape-room-update.component.html'
})
export class EscapeRoomUpdateComponent implements OnInit {
  escapeRoom: IEscapeRoom;
  isSaving: boolean;

  wlasciciels: IWlasciciel[];

  editForm = this.fb.group({
    id: [],
    zdjecie: [],
    zdjecieContentType: [],
    ulica: [],
    miasto: [],
    kodPocztowy: [],
    email: [],
    telefon: [],
    stronaWWW: [],
    nazwa: [],
    opis: [null, [Validators.required]],
    iloscOsob: [],
    cena: [],
    pktDoZdobycia: [],
    wymaganaIloscPkt: [],
    poziomTrudnosci: [null, [Validators.required]],
    czasNaPrzejscie: [],
    wlasciciels: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected escapeRoomService: EscapeRoomService,
    protected wlascicielService: WlascicielService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ escapeRoom }) => {
      this.updateForm(escapeRoom);
      this.escapeRoom = escapeRoom;
    });
    this.wlascicielService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IWlasciciel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IWlasciciel[]>) => response.body)
      )
      .subscribe((res: IWlasciciel[]) => (this.wlasciciels = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(escapeRoom: IEscapeRoom) {
    this.editForm.patchValue({
      id: escapeRoom.id,
      zdjecie: escapeRoom.zdjecie,
      zdjecieContentType: escapeRoom.zdjecieContentType,
      ulica: escapeRoom.ulica,
      miasto: escapeRoom.miasto,
      kodPocztowy: escapeRoom.kodPocztowy,
      email: escapeRoom.email,
      telefon: escapeRoom.telefon,
      stronaWWW: escapeRoom.stronaWWW,
      nazwa: escapeRoom.nazwa,
      opis: escapeRoom.opis,
      iloscOsob: escapeRoom.iloscOsob,
      cena: escapeRoom.cena,
      pktDoZdobycia: escapeRoom.pktDoZdobycia,
      wymaganaIloscPkt: escapeRoom.wymaganaIloscPkt,
      poziomTrudnosci: escapeRoom.poziomTrudnosci,
      czasNaPrzejscie: escapeRoom.czasNaPrzejscie,
      wlasciciels: escapeRoom.wlasciciels
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const escapeRoom = this.createFromForm();
    if (escapeRoom.id !== undefined) {
      this.subscribeToSaveResponse(this.escapeRoomService.update(escapeRoom));
    } else {
      this.subscribeToSaveResponse(this.escapeRoomService.create(escapeRoom));
    }
  }

  private createFromForm(): IEscapeRoom {
    const entity = {
      ...new EscapeRoom(),
      id: this.editForm.get(['id']).value,
      zdjecieContentType: this.editForm.get(['zdjecieContentType']).value,
      zdjecie: this.editForm.get(['zdjecie']).value,
      ulica: this.editForm.get(['ulica']).value,
      miasto: this.editForm.get(['miasto']).value,
      kodPocztowy: this.editForm.get(['kodPocztowy']).value,
      email: this.editForm.get(['email']).value,
      telefon: this.editForm.get(['telefon']).value,
      stronaWWW: this.editForm.get(['stronaWWW']).value,
      nazwa: this.editForm.get(['nazwa']).value,
      opis: this.editForm.get(['opis']).value,
      iloscOsob: this.editForm.get(['iloscOsob']).value,
      cena: this.editForm.get(['cena']).value,
      pktDoZdobycia: this.editForm.get(['pktDoZdobycia']).value,
      wymaganaIloscPkt: this.editForm.get(['wymaganaIloscPkt']).value,
      poziomTrudnosci: this.editForm.get(['poziomTrudnosci']).value,
      czasNaPrzejscie: this.editForm.get(['czasNaPrzejscie']).value,
      wlasciciels: this.editForm.get(['wlasciciels']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEscapeRoom>>) {
    result.subscribe((res: HttpResponse<IEscapeRoom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackWlascicielById(index: number, item: IWlasciciel) {
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
