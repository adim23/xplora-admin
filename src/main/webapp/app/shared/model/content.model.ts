import dayjs from 'dayjs';
import { ILanguage } from 'app/shared/model/language.model';
import { IUser } from 'app/shared/model/user.model';
import { IDestination } from 'app/shared/model/destination.model';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { IPlace } from 'app/shared/model/place.model';
import { IPlaceCategory } from 'app/shared/model/place-category.model';
import { ITourExtraCategory } from 'app/shared/model/tour-extra-category.model';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { IMenu } from 'app/shared/model/menu.model';
import { IWebPage } from 'app/shared/model/web-page.model';
import { ITag } from 'app/shared/model/tag.model';
import { ITourStep } from 'app/shared/model/tour-step.model';
import { IPromotion } from 'app/shared/model/promotion.model';
import { IImageFile } from 'app/shared/model/image-file.model';

export interface IContent {
  id?: number;
  code?: string;
  title?: string | null;
  shortDescription?: string | null;
  data?: string | null;
  meta?: string | null;
  createdDate?: dayjs.Dayjs | null;
  language?: ILanguage;
  createdBy?: IUser | null;
  destination?: IDestination | null;
  tourCategory?: ITourCategory | null;
  place?: IPlace | null;
  placeCategory?: IPlaceCategory | null;
  tourExtraCategory?: ITourExtraCategory | null;
  tourExtra?: ITourExtra | null;
  menu?: IMenu | null;
  webPage?: IWebPage | null;
  tag?: ITag | null;
  tourStep?: ITourStep | null;
  promotion?: IPromotion | null;
  imageFile?: IImageFile | null;
}

export const defaultValue: Readonly<IContent> = {};
