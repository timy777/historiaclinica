import dayjs from 'dayjs/esm';

export interface IProcesoMedico {
  id?: number;
  tipoProceso?: string;
  fechaInicio?: dayjs.Dayjs;
  fechaFin?: dayjs.Dayjs | null;
  estado?: string;
}

export class ProcesoMedico implements IProcesoMedico {
  constructor(
    public id?: number,
    public tipoProceso?: string,
    public fechaInicio?: dayjs.Dayjs,
    public fechaFin?: dayjs.Dayjs | null,
    public estado?: string
  ) {}
}

export function getProcesoMedicoIdentifier(procesoMedico: IProcesoMedico): number | undefined {
  return procesoMedico.id;
}
