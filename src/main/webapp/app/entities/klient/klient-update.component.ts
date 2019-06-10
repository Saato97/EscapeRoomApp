import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IKlient, Klient } from 'app/shared/model/klient.model';
import { KlientService } from './klient.service';
import { IOsoba } from 'app/shared/model/osoba.model';
import { OsobaService } from 'app/entities/osoba';

@Component({
  selector: 'jhi-klient-update',
  templateUrl: './klient-update.component.html'
})
export class KlientUpdateComponent implements OnInit {
  klient: IKlient;
  isSaving: boolean;

  osobas: IOsoba[];

  editForm = this.fb.group({
    id: [],
    telefon: [],
    email: [],
    osoba: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected klientService: KlientService,
    protected osobaService: OsobaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ klient }) => {
      this.updateForm(klient);
      this.klient = klient;
    });
    this.osobaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOsoba[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOsoba[]>) => response.body)
      )
      .subscribe((res: IOsoba[]) => (this.osobas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(klient: IKlient) {
    this.editForm.patchValue({
      id: klient.id,
      telefon: klient.telefon,
      email: klient.email,
      osoba: klient.osoba
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const klient = this.createFromForm();
    if (klient.id !== undefined) {
      this.subscribeToSaveResponse(this.klientService.update(klient));
    } else {
      this.subscribeToSaveResponse(this.klientService.create(klient));
    }
  }

  private createFromForm(): IKlient {
    const entity = {
      ...new Klient(),
      id: this.editForm.get(['id']).value,
      telefon: this.editForm.get(['telefon']).value,
      email: this.editForm.get(['email']).value,
      osoba: this.editForm.get(['osoba']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKlient>>) {
    result.subscribe((res: HttpResponse<IKlient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
