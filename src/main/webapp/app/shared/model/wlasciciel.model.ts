import { IOsoba } from 'app/shared/model/osoba.model';
import { IEscapeRoom } from 'app/shared/model/escape-room.model';

export interface IWlasciciel {
  id?: number;
  osoba?: IOsoba;
  escaperooms?: IEscapeRoom[];
}

export class Wlasciciel implements IWlasciciel {
  constructor(public id?: number, public osoba?: IOsoba, public escaperooms?: IEscapeRoom[]) {}
}
