import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';

export interface IPersonalMedico {
  id?: number;
  nombre?: string;
  especialidad?: string | null;
  telefonoContacto?: string | null;
  correo?: string | null;
  licenciaMedica?: string | null;
  consultaMedicas?: IConsultaMedica[] | null;
}

export class PersonalMedico implements IPersonalMedico {
  constructor(
    public id?: number,
    public nombre?: string,
    public especialidad?: string | null,
    public telefonoContacto?: string | null,
    public correo?: string | null,
    public licenciaMedica?: string | null,
    public consultaMedicas?: IConsultaMedica[] | null
  ) {}
}

export function getPersonalMedicoIdentifier(personalMedico: IPersonalMedico): number | undefined {
  return personalMedico.id;
}
