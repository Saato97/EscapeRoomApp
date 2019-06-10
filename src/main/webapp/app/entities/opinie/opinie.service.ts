import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOpinie } from 'app/shared/model/opinie.model';

type EntityResponseType = HttpResponse<IOpinie>;
type EntityArrayResponseType = HttpResponse<IOpinie[]>;

@Injectable({ providedIn: 'root' })
export class OpinieService {
  public resourceUrl = SERVER_API_URL + 'api/opinies';

  constructor(protected http: HttpClient) {}

  create(opinie: IOpinie): Observable<EntityResponseType> {
    return this.http.post<IOpinie>(this.resourceUrl, opinie, { observe: 'response' });
  }

  update(opinie: IOpinie): Observable<EntityResponseType> {
    return this.http.put<IOpinie>(this.resourceUrl, opinie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOpinie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOpinie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
