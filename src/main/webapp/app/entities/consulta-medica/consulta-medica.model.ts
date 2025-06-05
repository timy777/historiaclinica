import dayjs from 'dayjs/esm';
import { IEvaluacionFisica } from 'app/entities/evaluacion-fisica/evaluacion-fisica.model';
import { IMedicacion } from 'app/entities/medicacion/medicacion.model';
import { ITratamiento } from 'app/entities/tratamiento/tratamiento.model';
import { IEstudioMedico } from 'app/entities/estudio-medico/estudio-medico.model';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';

export interface IConsultaMedica {
  id?: number;
  diagnostico?: string;
  tratamientoSugerido?: string | null;
  observaciones?: string | null;
  fechaConsulta?: dayjs.Dayjs;
  evaluacionFisicas?: IEvaluacionFisica[] | null;
  medicacions?: IMedicacion[] | null;
  tratamientos?: ITratamiento[] | null;
  estudioMedicos?: IEstudioMedico[] | null;
  personalMedico?: IPersonalMedico | null;
  paciente?: IPaciente | null;
}

export class ConsultaMedica implements IConsultaMedica {
  constructor(
    public id?: number,
    public diagnostico?: string,
    public tratamientoSugerido?: string | null,
    public observaciones?: string | null,
    public fechaConsulta?: dayjs.Dayjs,
    public evaluacionFisicas?: IEvaluacionFisica[] | null,
    public medicacions?: IMedicacion[] | null,
    public tratamientos?: ITratamiento[] | null,
    public estudioMedicos?: IEstudioMedico[] | null,
    public personalMedico?: IPersonalMedico | null,
    public paciente?: IPaciente | null
  ) {}
}

export function getConsultaMedicaIdentifier(consultaMedica: IConsultaMedica): number | undefined {
  return consultaMedica.id;
}
