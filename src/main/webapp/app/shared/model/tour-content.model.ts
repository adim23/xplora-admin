import dayjs from 'dayjs';
import { ILanguage } from 'app/shared/model/language.model';

export interface ITourContent {
  id?: number;
  code?: string;
  title?: string | null;
  shortDescription?: string | null;
  data?: string | null;
  meta?: string | null;
  cancellation?: string | null;
  activityPath?: string | null;
  atAGlance?: string | null;
  inDetail?: string | null;
  whatIsIncluded?: string | null;
  youCanAdd?: string | null;
  importantInformation?: string | null;
  extraInfo?: string | null;
  createdDate?: dayjs.Dayjs | null;
  language?: ILanguage;
}

export const defaultValue: Readonly<ITourContent> = {};
