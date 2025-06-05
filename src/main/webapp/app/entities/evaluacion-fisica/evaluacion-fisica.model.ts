import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';

export interface IEvaluacionFisica {
  id?: number;
  presionArterial?: string | null;
  temperatura?: number | null;
  ritmoCardiaco?: number | null;
  frecuenciaRespiratoria?: number | null;
  peso?: number | null;
  altura?: number | null;
  consultaMedica?: IConsultaMedica | null;
}

export class EvaluacionFisica implements IEvaluacionFisica {
  constructor(
    public id?: number,
    public presionArterial?: string | null,
    public temperatura?: number | null,
    public ritmoCardiaco?: number | null,
    public frecuenciaRespiratoria?: number | null,
    public peso?: number | null,
    public altura?: number | null,
    public consultaMedica?: IConsultaMedica | null
  ) {}
}

export function getEvaluacionFisicaIdentifier(evaluacionFisica: IEvaluacionFisica): number | undefined {
  return evaluacionFisica.id;
}
