import dayjs from 'dayjs/esm';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';

export interface IEstudioMedico {
  id?: number;
  tipoEstudio?: string;
  resultado?: string | null;
  fechaRealizacion?: dayjs.Dayjs;
  consultaMedica?: IConsultaMedica | null;
}

export class EstudioMedico implements IEstudioMedico {
  constructor(
    public id?: number,
    public tipoEstudio?: string,
    public resultado?: string | null,
    public fechaRealizacion?: dayjs.Dayjs,
    public consultaMedica?: IConsultaMedica | null
  ) {}
}

export function getEstudioMedicoIdentifier(estudioMedico: IEstudioMedico): number | undefined {
  return estudioMedico.id;
}
