import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IOpinie, Opinie } from 'app/shared/model/opinie.model';
import { OpinieService } from './opinie.service';
import { IWizyty } from 'app/shared/model/wizyty.model';
import { WizytyService } from 'app/entities/wizyty';

@Component({
  selector: 'jhi-opinie-update',
  templateUrl: './opinie-update.component.html'
})
export class OpinieUpdateComponent implements OnInit {
  opinie: IOpinie;
  isSaving: boolean;

  wizyties: IWizyty[];

  editForm = this.fb.group({
    id: [],
    opinia: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected opinieService: OpinieService,
    protected wizytyService: WizytyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ opinie }) => {
      this.updateForm(opinie);
      this.opinie = opinie;
    });
    this.wizytyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IWizyty[]>) => mayBeOk.ok),
        map((response: HttpResponse<IWizyty[]>) => response.body)
      )
      .subscribe((res: IWizyty[]) => (this.wizyties = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(opinie: IOpinie) {
    this.editForm.patchValue({
      id: opinie.id,
      opinia: opinie.opinia
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const opinie = this.createFromForm();
    if (opinie.id !== undefined) {
      this.subscribeToSaveResponse(this.opinieService.update(opinie));
    } else {
      this.subscribeToSaveResponse(this.opinieService.create(opinie));
    }
  }

  private createFromForm(): IOpinie {
    const entity = {
      ...new Opinie(),
      id: this.editForm.get(['id']).value,
      opinia: this.editForm.get(['opinia']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpinie>>) {
    result.subscribe((res: HttpResponse<IOpinie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackWizytyById(index: number, item: IWizyty) {
    return item.id;
  }
}
