/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { EscapeRoomService } from 'app/entities/escape-room/escape-room.service';
import { IEscapeRoom, EscapeRoom, Poziom } from 'app/shared/model/escape-room.model';

describe('Service Tests', () => {
  describe('EscapeRoom Service', () => {
    let injector: TestBed;
    let service: EscapeRoomService;
    let httpMock: HttpTestingController;
    let elemDefault: IEscapeRoom;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EscapeRoomService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EscapeRoom(
        0,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        Poziom.LATWY,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a EscapeRoom', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new EscapeRoom(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a EscapeRoom', async () => {
        const returnedFromService = Object.assign(
          {
            zdjecie: 'BBBBBB',
            ulica: 'BBBBBB',
            miasto: 'BBBBBB',
            kodPocztowy: 'BBBBBB',
            email: 'BBBBBB',
            telefon: 'BBBBBB',
            stronaWWW: 'BBBBBB',
            nazwa: 'BBBBBB',
            opis: 'BBBBBB',
            iloscOsob: 'BBBBBB',
            cena: 1,
            pktDoZdobycia: 1,
            wymaganaIloscPkt: 1,
            poziomTrudnosci: 'BBBBBB',
            czasNaPrzejscie: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of EscapeRoom', async () => {
        const returnedFromService = Object.assign(
          {
            zdjecie: 'BBBBBB',
            ulica: 'BBBBBB',
            miasto: 'BBBBBB',
            kodPocztowy: 'BBBBBB',
            email: 'BBBBBB',
            telefon: 'BBBBBB',
            stronaWWW: 'BBBBBB',
            nazwa: 'BBBBBB',
            opis: 'BBBBBB',
            iloscOsob: 'BBBBBB',
            cena: 1,
            pktDoZdobycia: 1,
            wymaganaIloscPkt: 1,
            poziomTrudnosci: 'BBBBBB',
            czasNaPrzejscie: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EscapeRoom', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
