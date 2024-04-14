import dayjs from 'dayjs';

export interface IPassenger {
  id?: number;
  name?: string | null;
  email?: string | null;
  mobile?: string | null;
  age?: number | null;
  gender?: string | null;
  nationality?: string | null;
  createdDate?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IPassenger> = {};
