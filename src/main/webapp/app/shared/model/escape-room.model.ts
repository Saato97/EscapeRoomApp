import { IWlasciciel } from 'app/shared/model/wlasciciel.model';
import { IWizyty } from 'app/shared/model/wizyty.model';

export const enum Poziom {
  LATWY = 'LATWY',
  SREDNI = 'SREDNI',
  TRUDNY = 'TRUDNY',
  EXPERT = 'EXPERT'
}

export interface IEscapeRoom {
  id?: number;
  zdjecieContentType?: string;
  zdjecie?: any;
  ulica?: string;
  miasto?: string;
  kodPocztowy?: string;
  email?: string;
  telefon?: string;
  stronaWWW?: string;
  nazwa?: string;
  opis?: any;
  iloscOsob?: string;
  cena?: number;
  pktDoZdobycia?: number;
  wymaganaIloscPkt?: number;
  poziomTrudnosci?: Poziom;
  czasNaPrzejscie?: string;
  wlasciciels?: IWlasciciel[];
  wizyties?: IWizyty[];
}

export class EscapeRoom implements IEscapeRoom {
  constructor(
    public id?: number,
    public zdjecieContentType?: string,
    public zdjecie?: any,
    public ulica?: string,
    public miasto?: string,
    public kodPocztowy?: string,
    public email?: string,
    public telefon?: string,
    public stronaWWW?: string,
    public nazwa?: string,
    public opis?: any,
    public iloscOsob?: string,
    public cena?: number,
    public pktDoZdobycia?: number,
    public wymaganaIloscPkt?: number,
    public poziomTrudnosci?: Poziom,
    public czasNaPrzejscie?: string,
    public wlasciciels?: IWlasciciel[],
    public wizyties?: IWizyty[]
  ) {}
}
