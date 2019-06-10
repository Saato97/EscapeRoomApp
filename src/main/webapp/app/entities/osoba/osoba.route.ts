import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Osoba } from 'app/shared/model/osoba.model';
import { OsobaService } from './osoba.service';
import { OsobaComponent } from './osoba.component';
import { OsobaDetailComponent } from './osoba-detail.component';
import { OsobaUpdateComponent } from './osoba-update.component';
import { OsobaDeletePopupComponent } from './osoba-delete-dialog.component';
import { IOsoba } from 'app/shared/model/osoba.model';

@Injectable({ providedIn: 'root' })
export class OsobaResolve implements Resolve<IOsoba> {
  constructor(private service: OsobaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOsoba> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Osoba>) => response.ok),
        map((osoba: HttpResponse<Osoba>) => osoba.body)
      );
    }
    return of(new Osoba());
  }
}

export const osobaRoute: Routes = [
  {
    path: '',
    component: OsobaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.osoba.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OsobaDetailComponent,
    resolve: {
      osoba: OsobaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.osoba.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OsobaUpdateComponent,
    resolve: {
      osoba: OsobaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.osoba.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OsobaUpdateComponent,
    resolve: {
      osoba: OsobaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.osoba.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const osobaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OsobaDeletePopupComponent,
    resolve: {
      osoba: OsobaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.osoba.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
