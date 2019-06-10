import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EscapeRoomAppSharedModule } from 'app/shared';
import {
  WlascicielComponent,
  WlascicielDetailComponent,
  WlascicielUpdateComponent,
  WlascicielDeletePopupComponent,
  WlascicielDeleteDialogComponent,
  wlascicielRoute,
  wlascicielPopupRoute
} from './';

const ENTITY_STATES = [...wlascicielRoute, ...wlascicielPopupRoute];

@NgModule({
  imports: [EscapeRoomAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    WlascicielComponent,
    WlascicielDetailComponent,
    WlascicielUpdateComponent,
    WlascicielDeleteDialogComponent,
    WlascicielDeletePopupComponent
  ],
  entryComponents: [WlascicielComponent, WlascicielUpdateComponent, WlascicielDeleteDialogComponent, WlascicielDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EscapeRoomAppWlascicielModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
