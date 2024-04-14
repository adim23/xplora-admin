import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IDestination } from 'app/shared/model/destination.model';
import { ITour } from 'app/shared/model/tour.model';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { ITourExtraCategory } from 'app/shared/model/tour-extra-category.model';
import { IPlace } from 'app/shared/model/place.model';
import { IPlaceCategory } from 'app/shared/model/place-category.model';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { IDriver } from 'app/shared/model/driver.model';

export interface IImageFile {
  id?: number;
  code?: string;
  title?: string | null;
  alt?: string | null;
  filename?: string | null;
  dataContentType?: string | null;
  data?: string | null;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: IUser | null;
  destination?: IDestination | null;
  tour?: ITour | null;
  tourCategory?: ITourCategory | null;
  tourExtra?: ITourExtra | null;
  tourExtraCategory?: ITourExtraCategory | null;
  place?: IPlace | null;
  placeCategory?: IPlaceCategory | null;
  vehicle?: IVehicle | null;
  driver?: IDriver | null;
}

export const defaultValue: Readonly<IImageFile> = {};
