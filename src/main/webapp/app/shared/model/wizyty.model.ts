import { Moment } from 'moment';
import { IOpinie } from 'app/shared/model/opinie.model';
import { IKlient } from 'app/shared/model/klient.model';
import { IEscapeRoom } from 'app/shared/model/escape-room.model';

export interface IWizyty {
  id?: number;
  dataWizyty?: Moment;
  opinie?: IOpinie;
  klient?: IKlient;
  escaperoom?: IEscapeRoom;
}

export class Wizyty implements IWizyty {
  constructor(
    public id?: number,
    public dataWizyty?: Moment,
    public opinie?: IOpinie,
    public klient?: IKlient,
    public escaperoom?: IEscapeRoom
  ) {}
}
