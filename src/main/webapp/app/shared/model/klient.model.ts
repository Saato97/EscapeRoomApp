import { IOsoba } from 'app/shared/model/osoba.model';
import { IWizyty } from 'app/shared/model/wizyty.model';

export interface IKlient {
  id?: number;
  telefon?: string;
  email?: string;
  osoba?: IOsoba;
  wizyties?: IWizyty[];
}

export class Klient implements IKlient {
  constructor(public id?: number, public telefon?: string, public email?: string, public osoba?: IOsoba, public wizyties?: IWizyty[]) {}
}
