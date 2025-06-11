import dayjs from 'dayjs/esm';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';
import { ISalaMedica } from 'app/entities/sala-medica/sala-medica.model';

export interface IProcesoMedico {
  id?: number;
  tipoProceso?: string;
  fechaInicio?: dayjs.Dayjs;
  fechaFin?: dayjs.Dayjs | null;
  estado?: string;
  hashBlockchain?: string | null;
  paciente?: IPaciente | null;
  personalMedico?: IPersonalMedico | null;
  salaMedica?: ISalaMedica | null;
}

export class ProcesoMedico implements IProcesoMedico {
  constructor(
    public id?: number,
    public tipoProceso?: string,
    public fechaInicio?: dayjs.Dayjs,
    public fechaFin?: dayjs.Dayjs | null,
    public estado?: string,
    public hashBlockchain?: string | null,
    public paciente?: IPaciente | null,
    public personalMedico?: IPersonalMedico | null,
    public salaMedica?: ISalaMedica | null
  ) {}
}

export function getProcesoMedicoIdentifier(procesoMedico: IProcesoMedico): number | undefined {
  return procesoMedico.id;
}
