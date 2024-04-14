import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IPlace } from 'app/shared/model/place.model';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { ITag } from 'app/shared/model/tag.model';
import { IPromotion } from 'app/shared/model/promotion.model';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { IDestination } from 'app/shared/model/destination.model';
import { TourMode } from 'app/shared/model/enumerations/tour-mode.model';

export interface ITour {
  id?: number;
  code?: string;
  mode?: keyof typeof TourMode;
  duration?: number;
  petFriendly?: boolean;
  kidsAllowed?: boolean;
  availableFromDate?: dayjs.Dayjs | null;
  availableToDate?: dayjs.Dayjs | null;
  enabled?: boolean;
  initialPrice?: number | null;
  price?: number | null;
  badge?: string | null;
  rating?: number | null;
  widgetId?: string | null;
  externalId?: string | null;
  createdDate?: dayjs.Dayjs | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
  createdBy?: IUser | null;
  meetingPoint?: IPlace | null;
  finishPoint?: IPlace | null;
  tourExtras?: ITourExtra[] | null;
  tags?: ITag[] | null;
  promotions?: IPromotion[] | null;
  categories?: ITourCategory[] | null;
  destination?: IDestination | null;
}

export const defaultValue: Readonly<ITour> = {
  petFriendly: false,
  kidsAllowed: false,
  enabled: false,
};
