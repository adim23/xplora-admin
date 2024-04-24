import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IWebPage } from 'app/shared/model/web-page.model';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { IDestination } from 'app/shared/model/destination.model';

export interface IMenu {
  id?: number;
  code?: string;
  enabled?: boolean;
  icon?: string | null;
  uri?: string | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: IUser | null;
  page?: IWebPage | null;
  parent?: IMenu | null;
  tourCategory?: ITourCategory | null;
  destination?: IDestination | null;
}

export const defaultValue: Readonly<IMenu> = {
  enabled: false,
};
