import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITag } from 'app/shared/model/tag.model';

export interface IWebPage {
  id?: number;
  code?: string;
  enabled?: boolean;
  uriPath?: string | null;
  publishDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
  createdBy?: IUser | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<IWebPage> = {
  enabled: false,
};
