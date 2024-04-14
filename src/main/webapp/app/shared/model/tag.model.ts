import { IPlace } from 'app/shared/model/place.model';
import { ITour } from 'app/shared/model/tour.model';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { IWebPage } from 'app/shared/model/web-page.model';

export interface ITag {
  id?: number;
  code?: string;
  places?: IPlace[] | null;
  tours?: ITour[] | null;
  tourExtras?: ITourExtra[] | null;
  webPages?: IWebPage[] | null;
}

export const defaultValue: Readonly<ITag> = {};
