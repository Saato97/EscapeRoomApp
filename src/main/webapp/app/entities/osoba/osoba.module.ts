import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EscapeRoomAppSharedModule } from 'app/shared';
import {
  OsobaComponent,
  OsobaDetailComponent,
  OsobaUpdateComponent,
  OsobaDeletePopupComponent,
  OsobaDeleteDialogComponent,
  osobaRoute,
  osobaPopupRoute
} from './';

const ENTITY_STATES = [...osobaRoute, ...osobaPopupRoute];

@NgModule({
  imports: [EscapeRoomAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OsobaComponent, OsobaDetailComponent, OsobaUpdateComponent, OsobaDeleteDialogComponent, OsobaDeletePopupComponent],
  entryComponents: [OsobaComponent, OsobaUpdateComponent, OsobaDeleteDialogComponent, OsobaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EscapeRoomAppOsobaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
