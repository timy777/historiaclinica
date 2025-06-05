import dayjs from 'dayjs/esm';
import { IPaciente } from 'app/entities/paciente/paciente.model';

export interface ICitaMedica {
  id?: number;
  fechaCita?: dayjs.Dayjs;
  horaCita?: string;
  motivo?: string | null;
  estado?: string;
  paciente?: IPaciente | null;
}

export class CitaMedica implements ICitaMedica {
  constructor(
    public id?: number,
    public fechaCita?: dayjs.Dayjs,
    public horaCita?: string,
    public motivo?: string | null,
    public estado?: string,
    public paciente?: IPaciente | null
  ) {}
}

export function getCitaMedicaIdentifier(citaMedica: ICitaMedica): number | undefined {
  return citaMedica.id;
}
