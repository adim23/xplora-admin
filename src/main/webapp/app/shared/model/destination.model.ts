import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IDestination {
  id?: number;
  code?: string;
  createdDate?: dayjs.Dayjs | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdBy?: IUser | null;
}

export const defaultValue: Readonly<IDestination> = {};
