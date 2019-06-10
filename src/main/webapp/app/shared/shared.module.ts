import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { EscapeRoomAppSharedLibsModule, EscapeRoomAppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [EscapeRoomAppSharedLibsModule, EscapeRoomAppSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [EscapeRoomAppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EscapeRoomAppSharedModule {
  static forRoot() {
    return {
      ngModule: EscapeRoomAppSharedModule
    };
  }
}
