import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EscapeRoomAppSharedModule } from 'app/shared';
import {
  WizytyComponent,
  WizytyDetailComponent,
  WizytyUpdateComponent,
  WizytyDeletePopupComponent,
  WizytyDeleteDialogComponent,
  wizytyRoute,
  wizytyPopupRoute
} from './';

const ENTITY_STATES = [...wizytyRoute, ...wizytyPopupRoute];

@NgModule({
  imports: [EscapeRoomAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [WizytyComponent, WizytyDetailComponent, WizytyUpdateComponent, WizytyDeleteDialogComponent, WizytyDeletePopupComponent],
  entryComponents: [WizytyComponent, WizytyUpdateComponent, WizytyDeleteDialogComponent, WizytyDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EscapeRoomAppWizytyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
