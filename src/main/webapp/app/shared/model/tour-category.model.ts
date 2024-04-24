import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITour } from 'app/shared/model/tour.model';

export interface ITourCategory {
  id?: number;
  code?: string;
  enabled?: boolean;
  icon?: string | null;
  createdDate?: dayjs.Dayjs | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdBy?: IUser | null;
  parent?: ITourCategory | null;
  tours?: ITour[] | null;
}

export const defaultValue: Readonly<ITourCategory> = {
  enabled: false,
};
