import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';

export interface IMedicacion {
  id?: number;
  nombreMedicamento?: string;
  dosis?: string | null;
  frecuencia?: string | null;
  viaAdministracion?: string | null;
  consultaMedica?: IConsultaMedica | null;
}

export class Medicacion implements IMedicacion {
  constructor(
    public id?: number,
    public nombreMedicamento?: string,
    public dosis?: string | null,
    public frecuencia?: string | null,
    public viaAdministracion?: string | null,
    public consultaMedica?: IConsultaMedica | null
  ) {}
}

export function getMedicacionIdentifier(medicacion: IMedicacion): number | undefined {
  return medicacion.id;
}
