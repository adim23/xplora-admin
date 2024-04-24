import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITag } from 'app/shared/model/tag.model';
import { IPlaceCategory } from 'app/shared/model/place-category.model';
import { IDestination } from 'app/shared/model/destination.model';

export interface IPlace {
  id?: number;
  code?: string;
  enabled?: boolean;
  icon?: string | null;
  destinationSight?: boolean;
  longitude?: string | null;
  latitude?: string | null;
  createdDate?: dayjs.Dayjs | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdBy?: IUser | null;
  tags?: ITag[] | null;
  categories?: IPlaceCategory[] | null;
  destination?: IDestination | null;
}

export const defaultValue: Readonly<IPlace> = {
  enabled: false,
  destinationSight: false,
};
