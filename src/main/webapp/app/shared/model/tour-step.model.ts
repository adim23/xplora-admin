import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITour } from 'app/shared/model/tour.model';
import { IPlace } from 'app/shared/model/place.model';

export interface ITourStep {
  id?: number;
  code?: string;
  stepOrder?: number;
  waitTime?: number;
  driveTime?: number;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: IUser | null;
  tour?: ITour;
  place?: IPlace;
}

export const defaultValue: Readonly<ITourStep> = {};
