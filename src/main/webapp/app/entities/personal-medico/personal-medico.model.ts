import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { ICitaMedica } from 'app/entities/cita-medica/cita-medica.model';
import { IProcesoMedico } from 'app/entities/proceso-medico/proceso-medico.model';

export interface IPersonalMedico {
  id?: number;
  nombre?: string;
  especialidad?: string | null;
  telefonoContacto?: string | null;
  correo?: string | null;
  licenciaMedica?: string | null;
  hashBlockchain?: string | null;
  consultaMedicas?: IConsultaMedica[] | null;
  pacientes?: IPaciente[] | null;
  citaMedicas?: ICitaMedica[] | null;
  procesoMedicos?: IProcesoMedico[] | null;
}

export class PersonalMedico implements IPersonalMedico {
  constructor(
    public id?: number,
    public nombre?: string,
    public especialidad?: string | null,
    public telefonoContacto?: string | null,
    public correo?: string | null,
    public licenciaMedica?: string | null,
    public hashBlockchain?: string | null,
    public consultaMedicas?: IConsultaMedica[] | null,
    public pacientes?: IPaciente[] | null,
    public citaMedicas?: ICitaMedica[] | null,
    public procesoMedicos?: IProcesoMedico[] | null
  ) {}
}

export function getPersonalMedicoIdentifier(personalMedico: IPersonalMedico): number | undefined {
  return personalMedico.id;
}
