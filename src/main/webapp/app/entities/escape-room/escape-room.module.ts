import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EscapeRoomAppSharedModule } from 'app/shared';
import {
  EscapeRoomComponent,
  EscapeRoomDetailComponent,
  EscapeRoomUpdateComponent,
  EscapeRoomDeletePopupComponent,
  EscapeRoomDeleteDialogComponent,
  escapeRoomRoute,
  escapeRoomPopupRoute
} from './';

const ENTITY_STATES = [...escapeRoomRoute, ...escapeRoomPopupRoute];

@NgModule({
  imports: [EscapeRoomAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EscapeRoomComponent,
    EscapeRoomDetailComponent,
    EscapeRoomUpdateComponent,
    EscapeRoomDeleteDialogComponent,
    EscapeRoomDeletePopupComponent
  ],
  entryComponents: [EscapeRoomComponent, EscapeRoomUpdateComponent, EscapeRoomDeleteDialogComponent, EscapeRoomDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EscapeRoomAppEscapeRoomModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
