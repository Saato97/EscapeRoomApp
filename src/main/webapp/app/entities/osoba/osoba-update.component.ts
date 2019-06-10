import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IOsoba, Osoba } from 'app/shared/model/osoba.model';
import { OsobaService } from './osoba.service';

@Component({
  selector: 'jhi-osoba-update',
  templateUrl: './osoba-update.component.html'
})
export class OsobaUpdateComponent implements OnInit {
  osoba: IOsoba;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    imie: [],
    nazwisko: [],
    login: [null, [Validators.required]],
    haslo: [null, [Validators.required]]
  });

  constructor(protected osobaService: OsobaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ osoba }) => {
      this.updateForm(osoba);
      this.osoba = osoba;
    });
  }

  updateForm(osoba: IOsoba) {
    this.editForm.patchValue({
      id: osoba.id,
      imie: osoba.imie,
      nazwisko: osoba.nazwisko,
      login: osoba.login,
      haslo: osoba.haslo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const osoba = this.createFromForm();
    if (osoba.id !== undefined) {
      this.subscribeToSaveResponse(this.osobaService.update(osoba));
    } else {
      this.subscribeToSaveResponse(this.osobaService.create(osoba));
    }
  }

  private createFromForm(): IOsoba {
    const entity = {
      ...new Osoba(),
      id: this.editForm.get(['id']).value,
      imie: this.editForm.get(['imie']).value,
      nazwisko: this.editForm.get(['nazwisko']).value,
      login: this.editForm.get(['login']).value,
      haslo: this.editForm.get(['haslo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOsoba>>) {
    result.subscribe((res: HttpResponse<IOsoba>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
