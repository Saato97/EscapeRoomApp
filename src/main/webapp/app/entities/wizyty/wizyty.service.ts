import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWizyty } from 'app/shared/model/wizyty.model';

type EntityResponseType = HttpResponse<IWizyty>;
type EntityArrayResponseType = HttpResponse<IWizyty[]>;

@Injectable({ providedIn: 'root' })
export class WizytyService {
  public resourceUrl = SERVER_API_URL + 'api/wizyties';

  constructor(protected http: HttpClient) {}

  create(wizyty: IWizyty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wizyty);
    return this.http
      .post<IWizyty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(wizyty: IWizyty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wizyty);
    return this.http
      .put<IWizyty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWizyty>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWizyty[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(wizyty: IWizyty): IWizyty {
    const copy: IWizyty = Object.assign({}, wizyty, {
      dataWizyty: wizyty.dataWizyty != null && wizyty.dataWizyty.isValid() ? wizyty.dataWizyty.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataWizyty = res.body.dataWizyty != null ? moment(res.body.dataWizyty) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((wizyty: IWizyty) => {
        wizyty.dataWizyty = wizyty.dataWizyty != null ? moment(wizyty.dataWizyty) : null;
      });
    }
    return res;
  }
}
