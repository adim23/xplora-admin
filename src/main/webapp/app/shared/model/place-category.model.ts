import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IPlace } from 'app/shared/model/place.model';

export interface IPlaceCategory {
  id?: number;
  code?: string;
  icon?: string | null;
  enabled?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdBy?: IUser | null;
  places?: IPlace[] | null;
}

export const defaultValue: Readonly<IPlaceCategory> = {
  enabled: false,
};
