import { IKlient } from 'app/shared/model/klient.model';
import { IWlasciciel } from 'app/shared/model/wlasciciel.model';

export interface IOsoba {
  id?: number;
  imie?: string;
  nazwisko?: string;
  login?: string;
  haslo?: string;
  klients?: IKlient[];
  wlasciciels?: IWlasciciel[];
}

export class Osoba implements IOsoba {
  constructor(
    public id?: number,
    public imie?: string,
    public nazwisko?: string,
    public login?: string,
    public haslo?: string,
    public klients?: IKlient[],
    public wlasciciels?: IWlasciciel[]
  ) {}
}
