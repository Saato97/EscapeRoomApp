import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EscapeRoom } from 'app/shared/model/escape-room.model';
import { EscapeRoomService } from './escape-room.service';
import { EscapeRoomComponent } from './escape-room.component';
import { EscapeRoomDetailComponent } from './escape-room-detail.component';
import { EscapeRoomUpdateComponent } from './escape-room-update.component';
import { EscapeRoomDeletePopupComponent } from './escape-room-delete-dialog.component';
import { IEscapeRoom } from 'app/shared/model/escape-room.model';

@Injectable({ providedIn: 'root' })
export class EscapeRoomResolve implements Resolve<IEscapeRoom> {
  constructor(private service: EscapeRoomService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEscapeRoom> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EscapeRoom>) => response.ok),
        map((escapeRoom: HttpResponse<EscapeRoom>) => escapeRoom.body)
      );
    }
    return of(new EscapeRoom());
  }
}

export const escapeRoomRoute: Routes = [
  {
    path: '',
    component: EscapeRoomComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.escapeRoom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EscapeRoomDetailComponent,
    resolve: {
      escapeRoom: EscapeRoomResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.escapeRoom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EscapeRoomUpdateComponent,
    resolve: {
      escapeRoom: EscapeRoomResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.escapeRoom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EscapeRoomUpdateComponent,
    resolve: {
      escapeRoom: EscapeRoomResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.escapeRoom.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const escapeRoomPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EscapeRoomDeletePopupComponent,
    resolve: {
      escapeRoom: EscapeRoomResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'escapeRoomApp.escapeRoom.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
