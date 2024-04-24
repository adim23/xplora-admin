import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITour } from 'app/shared/model/tour.model';
import { IPlace } from 'app/shared/model/place.model';
import { DurationMeasure } from 'app/shared/model/enumerations/duration-measure.model';

export interface ITourStep {
  id?: number;
  code?: string;
  enabled?: boolean;
  icon?: string | null;
  stepOrder?: number;
  waitTime?: number;
  waitTimeMeasure?: keyof typeof DurationMeasure;
  driveTime?: number;
  driveTimeMeasure?: keyof typeof DurationMeasure;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: IUser | null;
  tour?: ITour;
  place?: IPlace;
}

export const defaultValue: Readonly<ITourStep> = {
  enabled: false,
};
