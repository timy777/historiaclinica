import dayjs from 'dayjs/esm';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ICitaMedica } from 'app/entities/cita-medica/cita-medica.model';
import { IProcesoMedico } from 'app/entities/proceso-medico/proceso-medico.model';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';

export interface IPaciente {
  id?: number;
  nombre?: string;
  fechaNacimiento?: dayjs.Dayjs;
  genero?: string | null;
  direccion?: string | null;
  carnetidentidad?: string;
  email?: string;
  password?: string;
  telefonoContacto?: string | null;
  historialMedico?: string | null;
  consultaMedicas?: IConsultaMedica[] | null;
  citaMedicas?: ICitaMedica[] | null;
  procesoMedicos?: IProcesoMedico[] | null;
  personalMedicos?: IPersonalMedico[] | null;
}

export class Paciente implements IPaciente {
  constructor(
    public id?: number,
    public nombre?: string,
    public fechaNacimiento?: dayjs.Dayjs,
    public genero?: string | null,
    public direccion?: string | null,
    public carnetidentidad?: string,
    public email?: string,
    public password?: string,
    public telefonoContacto?: string | null,
    public historialMedico?: string | null,
    public consultaMedicas?: IConsultaMedica[] | null,
    public citaMedicas?: ICitaMedica[] | null,
    public procesoMedicos?: IProcesoMedico[] | null,
    public personalMedicos?: IPersonalMedico[] | null
  ) {}
}

export function getPacienteIdentifier(paciente: IPaciente): number | undefined {
  return paciente.id;
}
