import dayjs from 'dayjs';
import { ITour } from 'app/shared/model/tour.model';

export interface IPromotion {
  id?: number;
  code?: string;
  enabled?: boolean | null;
  discount?: number | null;
  fromDate?: dayjs.Dayjs | null;
  toDate?: dayjs.Dayjs | null;
  tours?: ITour[] | null;
}

export const defaultValue: Readonly<IPromotion> = {
  enabled: false,
};
