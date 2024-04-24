import { ILanguage } from 'app/shared/model/language.model';

export interface IPrompt {
  id?: number;
  key?: string | null;
  value?: string | null;
  language?: ILanguage;
}

export const defaultValue: Readonly<IPrompt> = {};
