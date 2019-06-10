import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Klient } from 'app/shared/model/klient.model';
import { KlientService } from './klient.service';
import { KlientComponent } from './klient.component';
import { KlientDetailComponent } from './klient-detail.component';
import { KlientUpdateComponent } from './klient-update.component';
import { KlientDeletePopupComponent } from './klient-delete-dialog.component';
import { IKlient } from 'app/shared/model/klient.model';

@Injectable({ providedIn: 'root' })
export class KlientResolve implements Resolve<IKlient> {
  constructor(private service: KlientService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IKlient> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Klient>) => response.ok),
        map((klient: HttpResponse<Klient>) => klient.body)
      );
    }
    return of(new Klient());
  }
}

export const klientRoute: Routes = [
  {
    path: '',
    component: KlientComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.klient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: KlientDetailComponent,
    resolve: {
      klient: KlientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.klient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: KlientUpdateComponent,
    resolve: {
      klient: KlientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.klient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: KlientUpdateComponent,
    resolve: {
      klient: KlientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.klient.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const klientPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: KlientDeletePopupComponent,
    resolve: {
      klient: KlientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.klient.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
