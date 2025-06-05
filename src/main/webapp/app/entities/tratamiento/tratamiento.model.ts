import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';

export interface ITratamiento {
  id?: number;
  tipoTratamiento?: string;
  duracion?: string | null;
  objetivo?: string | null;
  consultaMedica?: IConsultaMedica | null;
}

export class Tratamiento implements ITratamiento {
  constructor(
    public id?: number,
    public tipoTratamiento?: string,
    public duracion?: string | null,
    public objetivo?: string | null,
    public consultaMedica?: IConsultaMedica | null
  ) {}
}

export function getTratamientoIdentifier(tratamiento: ITratamiento): number | undefined {
  return tratamiento.id;
}
