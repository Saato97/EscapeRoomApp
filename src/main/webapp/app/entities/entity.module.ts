import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'osoba',
        loadChildren: './osoba/osoba.module#EscapeRoomAppOsobaModule'
      },
      {
        path: 'wlasciciel',
        loadChildren: './wlasciciel/wlasciciel.module#EscapeRoomAppWlascicielModule'
      },
      {
        path: 'klient',
        loadChildren: './klient/klient.module#EscapeRoomAppKlientModule'
      },
      {
        path: 'opinie',
        loadChildren: './opinie/opinie.module#EscapeRoomAppOpinieModule'
      },
      {
        path: 'wizyty',
        loadChildren: './wizyty/wizyty.module#EscapeRoomAppWizytyModule'
      },
      {
        path: 'escape-room',
        loadChildren: './escape-room/escape-room.module#EscapeRoomAppEscapeRoomModule'
      },
      {
        path: 'osoba',
        loadChildren: './osoba/osoba.module#EscapeRoomAppOsobaModule'
      },
      {
        path: 'escape-room',
        loadChildren: './escape-room/escape-room.module#EscapeRoomAppEscapeRoomModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EscapeRoomAppEntityModule {}
