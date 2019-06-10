import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEscapeRoom } from 'app/shared/model/escape-room.model';

type EntityResponseType = HttpResponse<IEscapeRoom>;
type EntityArrayResponseType = HttpResponse<IEscapeRoom[]>;

@Injectable({ providedIn: 'root' })
export class EscapeRoomService {
  public resourceUrl = SERVER_API_URL + 'api/escape-rooms';

  constructor(protected http: HttpClient) {}

  create(escapeRoom: IEscapeRoom): Observable<EntityResponseType> {
    return this.http.post<IEscapeRoom>(this.resourceUrl, escapeRoom, { observe: 'response' });
  }

  update(escapeRoom: IEscapeRoom): Observable<EntityResponseType> {
    return this.http.put<IEscapeRoom>(this.resourceUrl, escapeRoom, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEscapeRoom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscapeRoom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
