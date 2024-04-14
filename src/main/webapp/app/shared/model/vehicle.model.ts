import dayjs from 'dayjs';

export interface IVehicle {
  id?: number;
  plate?: string;
  type?: string;
  capacity?: number;
  color?: string | null;
  createdDate?: dayjs.Dayjs | null;
  defaultImage?: string | null;
  defaultImageDataContentType?: string | null;
  defaultImageData?: string | null;
}

export const defaultValue: Readonly<IVehicle> = {};
