import { IProcesoMedico } from 'app/entities/proceso-medico/proceso-medico.model';

export interface ISalaMedica {
  id?: number;
  nroSala?: number;
  nombre?: string;
  ubicacion?: string | null;
  equipamiento?: string | null;
  procesoMedicos?: IProcesoMedico[] | null;
}

export class SalaMedica implements ISalaMedica {
  constructor(
    public id?: number,
    public nroSala?: number,
    public nombre?: string,
    public ubicacion?: string | null,
    public equipamiento?: string | null,
    public procesoMedicos?: IProcesoMedico[] | null
  ) {}
}

export function getSalaMedicaIdentifier(salaMedica: ISalaMedica): number | undefined {
  return salaMedica.id;
}
