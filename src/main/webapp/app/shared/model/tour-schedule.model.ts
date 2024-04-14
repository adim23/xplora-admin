import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITour } from 'app/shared/model/tour.model';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { IDriver } from 'app/shared/model/driver.model';

export interface ITourSchedule {
  id?: number;
  code?: string;
  startDatetime?: dayjs.Dayjs;
  noPassengers?: number | null;
  noKids?: number | null;
  noPets?: number | null;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: IUser | null;
  tour?: ITour;
  vehicle?: IVehicle | null;
  driver?: IDriver | null;
}

export const defaultValue: Readonly<ITourSchedule> = {};
