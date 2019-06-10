import { IWizyty } from 'app/shared/model/wizyty.model';

export interface IOpinie {
  id?: number;
  opinia?: any;
  wizyty?: IWizyty;
}

export class Opinie implements IOpinie {
  constructor(public id?: number, public opinia?: any, public wizyty?: IWizyty) {}
}
