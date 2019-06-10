import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Wizyty } from 'app/shared/model/wizyty.model';
import { WizytyService } from './wizyty.service';
import { WizytyComponent } from './wizyty.component';
import { WizytyDetailComponent } from './wizyty-detail.component';
import { WizytyUpdateComponent } from './wizyty-update.component';
import { WizytyDeletePopupComponent } from './wizyty-delete-dialog.component';
import { IWizyty } from 'app/shared/model/wizyty.model';

@Injectable({ providedIn: 'root' })
export class WizytyResolve implements Resolve<IWizyty> {
  constructor(private service: WizytyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWizyty> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Wizyty>) => response.ok),
        map((wizyty: HttpResponse<Wizyty>) => wizyty.body)
      );
    }
    return of(new Wizyty());
  }
}

export const wizytyRoute: Routes = [
  {
    path: '',
    component: WizytyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wizyty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WizytyDetailComponent,
    resolve: {
      wizyty: WizytyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wizyty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WizytyUpdateComponent,
    resolve: {
      wizyty: WizytyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wizyty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WizytyUpdateComponent,
    resolve: {
      wizyty: WizytyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wizyty.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const wizytyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: WizytyDeletePopupComponent,
    resolve: {
      wizyty: WizytyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.wizyty.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
