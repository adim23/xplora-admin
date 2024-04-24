import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITag } from 'app/shared/model/tag.model';
import { ITourExtraCategory } from 'app/shared/model/tour-extra-category.model';
import { ITour } from 'app/shared/model/tour.model';

export interface ITourExtra {
  id?: number;
  code?: string;
  enabled?: boolean;
  icon?: string | null;
  price?: number | null;
  offer?: number | null;
  shopProductId?: string | null;
  shopUrl?: string | null;
  createdDate?: dayjs.Dayjs | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdBy?: IUser | null;
  tags?: ITag[] | null;
  categories?: ITourExtraCategory[] | null;
  tours?: ITour[] | null;
}

export const defaultValue: Readonly<ITourExtra> = {
  enabled: false,
};
