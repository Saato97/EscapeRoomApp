import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Opinie } from 'app/shared/model/opinie.model';
import { OpinieService } from './opinie.service';
import { OpinieComponent } from './opinie.component';
import { OpinieDetailComponent } from './opinie-detail.component';
import { OpinieUpdateComponent } from './opinie-update.component';
import { OpinieDeletePopupComponent } from './opinie-delete-dialog.component';
import { IOpinie } from 'app/shared/model/opinie.model';

@Injectable({ providedIn: 'root' })
export class OpinieResolve implements Resolve<IOpinie> {
  constructor(private service: OpinieService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOpinie> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Opinie>) => response.ok),
        map((opinie: HttpResponse<Opinie>) => opinie.body)
      );
    }
    return of(new Opinie());
  }
}

export const opinieRoute: Routes = [
  {
    path: '',
    component: OpinieComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.opinie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OpinieDetailComponent,
    resolve: {
      opinie: OpinieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.opinie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OpinieUpdateComponent,
    resolve: {
      opinie: OpinieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.opinie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OpinieUpdateComponent,
    resolve: {
      opinie: OpinieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.opinie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const opiniePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OpinieDeletePopupComponent,
    resolve: {
      opinie: OpinieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.opinie.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
