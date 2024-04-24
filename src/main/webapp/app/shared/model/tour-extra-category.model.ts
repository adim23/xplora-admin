import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITourExtra } from 'app/shared/model/tour-extra.model';

export interface ITourExtraCategory {
  id?: number;
  code?: string;
  enabled?: boolean;
  icon?: string | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  shopCategoryId?: string | null;
  shopUrl?: string | null;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: IUser | null;
  extras?: ITourExtra[] | null;
}

export const defaultValue: Readonly<ITourExtraCategory> = {
  enabled: false,
};
