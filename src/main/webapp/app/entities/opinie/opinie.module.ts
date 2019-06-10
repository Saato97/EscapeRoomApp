import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EscapeRoomAppSharedModule } from 'app/shared';
import {
  OpinieComponent,
  OpinieDetailComponent,
  OpinieUpdateComponent,
  OpinieDeletePopupComponent,
  OpinieDeleteDialogComponent,
  opinieRoute,
  opiniePopupRoute
} from './';

const ENTITY_STATES = [...opinieRoute, ...opiniePopupRoute];

@NgModule({
  imports: [EscapeRoomAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OpinieComponent, OpinieDetailComponent, OpinieUpdateComponent, OpinieDeleteDialogComponent, OpinieDeletePopupComponent],
  entryComponents: [OpinieComponent, OpinieUpdateComponent, OpinieDeleteDialogComponent, OpinieDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EscapeRoomAppOpinieModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
