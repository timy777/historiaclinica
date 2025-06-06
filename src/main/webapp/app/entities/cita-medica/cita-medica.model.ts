import dayjs from 'dayjs/esm';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';

export interface ICitaMedica {
  id?: number;
  fechaCita?: dayjs.Dayjs;
  horaCita?: string;
  motivo?: string | null;
  estado?: string;
  paciente?: IPaciente | null;
  personalMedico?: IPersonalMedico | null;
}

export class CitaMedica implements ICitaMedica {
  constructor(
    public id?: number,
    public fechaCita?: dayjs.Dayjs,
    public horaCita?: string,
    public motivo?: string | null,
    public estado?: string,
    public paciente?: IPaciente | null,
    public personalMedico?: IPersonalMedico | null
  ) {}
}

export function getCitaMedicaIdentifier(citaMedica: ICitaMedica): number | undefined {
  return citaMedica.id;
}
