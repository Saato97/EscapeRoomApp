import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EscapeRoomAppSharedModule } from 'app/shared';
import {
  KlientComponent,
  KlientDetailComponent,
  KlientUpdateComponent,
  KlientDeletePopupComponent,
  KlientDeleteDialogComponent,
  klientRoute,
  klientPopupRoute
} from './';

const ENTITY_STATES = [...klientRoute, ...klientPopupRoute];

@NgModule({
  imports: [EscapeRoomAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [KlientComponent, KlientDetailComponent, KlientUpdateComponent, KlientDeleteDialogComponent, KlientDeletePopupComponent],
  entryComponents: [KlientComponent, KlientUpdateComponent, KlientDeleteDialogComponent, KlientDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EscapeRoomAppKlientModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
