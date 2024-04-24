import dayjs from 'dayjs';
import { ITourContent } from 'app/shared/model/tour-content.model';
import { IUser } from 'app/shared/model/user.model';
import { IPlace } from 'app/shared/model/place.model';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { ITag } from 'app/shared/model/tag.model';
import { IPromotion } from 'app/shared/model/promotion.model';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { IDestination } from 'app/shared/model/destination.model';
import { TourKind } from 'app/shared/model/enumerations/tour-kind.model';
import { TourMode } from 'app/shared/model/enumerations/tour-mode.model';
import { DurationMeasure } from 'app/shared/model/enumerations/duration-measure.model';

export interface ITour {
  id?: number;
  code?: string;
  enabled?: boolean;
  kind?: keyof typeof TourKind;
  mode?: keyof typeof TourMode | null;
  icon?: string | null;
  duration?: number;
  durationMeasure?: keyof typeof DurationMeasure;
  petFriendly?: boolean;
  kidsAllowed?: boolean;
  smoking?: boolean;
  availableFromDate?: dayjs.Dayjs | null;
  availableToDate?: dayjs.Dayjs | null;
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
  accessibility?: boolean | null;
  audioGuide?: boolean | null;
  tourGuide?: boolean | null;
  cssStyle?: string | null;
  departure?: dayjs.Dayjs | null;
  returnTime?: dayjs.Dayjs | null;
  testIn?: dayjs.Dayjs | null;
  testZ?: dayjs.Dayjs | null;
  dur?: string | null;
  content?: ITourContent | null;
  createdBy?: IUser | null;
  meetingPoint?: IPlace | null;
  finishPoint?: IPlace | null;
  tourExtras?: ITourExtra[] | null;
  tags?: ITag[] | null;
  promotions?: IPromotion[] | null;
  categories?: ITourCategory[] | null;
  destination?: IDestination | null;
  defaultCategory?: ITourCategory | null;
}

export const defaultValue: Readonly<ITour> = {
  enabled: false,
  petFriendly: false,
  kidsAllowed: false,
  smoking: false,
  accessibility: false,
  audioGuide: false,
  tourGuide: false,
};
