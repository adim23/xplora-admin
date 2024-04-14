import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface ILanguage {
  id?: number;
  code?: string;
  createdDate?: dayjs.Dayjs | null;
  icon?: string | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdBy?: IUser | null;
}

export const defaultValue: Readonly<ILanguage> = {};
