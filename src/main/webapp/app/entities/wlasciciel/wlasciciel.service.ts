import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWlasciciel } from 'app/shared/model/wlasciciel.model';

type EntityResponseType = HttpResponse<IWlasciciel>;
type EntityArrayResponseType = HttpResponse<IWlasciciel[]>;

@Injectable({ providedIn: 'root' })
export class WlascicielService {
  public resourceUrl = SERVER_API_URL + 'api/wlasciciels';

  constructor(protected http: HttpClient) {}

  create(wlasciciel: IWlasciciel): Observable<EntityResponseType> {
    return this.http.post<IWlasciciel>(this.resourceUrl, wlasciciel, { observe: 'response' });
  }

  update(wlasciciel: IWlasciciel): Observable<EntityResponseType> {
    return this.http.put<IWlasciciel>(this.resourceUrl, wlasciciel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWlasciciel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWlasciciel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
