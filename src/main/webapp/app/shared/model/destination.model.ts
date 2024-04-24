import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IDestination {
  id?: number;
  code?: string;
  enabled?: boolean;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  cssStyle?: string | null;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: IUser | null;
}

export const defaultValue: Readonly<IDestination> = {
  enabled: false,
};
