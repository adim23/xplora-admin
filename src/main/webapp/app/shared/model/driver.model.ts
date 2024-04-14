import dayjs from 'dayjs';

export interface IDriver {
  id?: number;
  name?: string;
  hiredAt?: dayjs.Dayjs | null;
  age?: number | null;
  email?: string | null;
  mobile?: string | null;
  createdDate?: dayjs.Dayjs | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
}

export const defaultValue: Readonly<IDriver> = {};
