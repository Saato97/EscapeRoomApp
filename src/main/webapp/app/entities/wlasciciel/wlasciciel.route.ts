import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Wlasciciel } from 'app/shared/model/wlasciciel.model';
import { WlascicielService } from './wlasciciel.service';
import { WlascicielComponent } from './wlasciciel.component';
import { WlascicielDetailComponent } from './wlasciciel-detail.component';
import { WlascicielUpdateComponent } from './wlasciciel-update.component';
import { WlascicielDeletePopupComponent } from './wlasciciel-delete-dialog.component';
import { IWlasciciel } from 'app/shared/model/wlasciciel.model';

@Injectable({ providedIn: 'root' })
export class WlascicielResolve implements Resolve<IWlasciciel> {
  constructor(private service: WlascicielService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWlasciciel> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Wlasciciel>) => response.ok),
        map((wlasciciel: HttpResponse<Wlasciciel>) => wlasciciel.body)
      );
    }
    return of(new Wlasciciel());
  }
}

export const wlascicielRoute: Routes = [
  {
    path: '',
    component: WlascicielComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wlasciciel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WlascicielDetailComponent,
    resolve: {
      wlasciciel: WlascicielResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wlasciciel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WlascicielUpdateComponent,
    resolve: {
      wlasciciel: WlascicielResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wlasciciel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WlascicielUpdateComponent,
    resolve: {
      wlasciciel: WlascicielResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wlasciciel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const wlascicielPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: WlascicielDeletePopupComponent,
    resolve: {
      wlasciciel: WlascicielResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wlasciciel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
