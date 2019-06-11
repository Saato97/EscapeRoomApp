import { Moment } from 'moment';
import { IOpinie } from 'app/shared/model/opinie.model';
import { IUser } from 'app/core/user/user.model';
import { IEscapeRoom } from 'app/shared/model/escape-room.model';

export interface IWizyty {
  id?: number;
  dataWizyty?: Moment;
  opinie?: IOpinie;
  user?: IUser;
  escapeRoom?: IEscapeRoom;
}

export class Wizyty implements IWizyty {
  constructor(
    public id?: number,
    public dataWizyty?: Moment,
    public opinie?: IOpinie,
    public user?: IUser,
    public escapeRoom?: IEscapeRoom
  ) {}
}
