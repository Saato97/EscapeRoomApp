import { IEscapeRoom } from 'app/shared/model/escape-room.model';

export interface IWlasciciel {
  id?: number;
  imie?: string;
  nazwisko?: string;
  escapeRooms?: IEscapeRoom[];
}

export class Wlasciciel implements IWlasciciel {
  constructor(public id?: number, public imie?: string, public nazwisko?: string, public escapeRooms?: IEscapeRoom[]) {}
}
