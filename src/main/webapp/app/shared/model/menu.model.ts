import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IWebPage } from 'app/shared/model/web-page.model';
import { ITourCategory } from 'app/shared/model/tour-category.model';

export interface IMenu {
  id?: number;
  code?: string;
  uri?: string | null;
  createdDate?: dayjs.Dayjs | null;
  icon?: string | null;
  enabled?: boolean | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdBy?: IUser | null;
  page?: IWebPage | null;
  parent?: IMenu | null;
  tourCategory?: ITourCategory | null;
}

export const defaultValue: Readonly<IMenu> = {
  enabled: false,
};
