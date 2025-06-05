import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'sala-medica',
        data: { pageTitle: 'historiacApp.salaMedica.home.title' },
        loadChildren: () => import('./sala-medica/sala-medica.module').then(m => m.SalaMedicaModule),
      },
      {
        path: 'proceso-medico',
        data: { pageTitle: 'historiacApp.procesoMedico.home.title' },
        loadChildren: () => import('./proceso-medico/proceso-medico.module').then(m => m.ProcesoMedicoModule),
      },
      {
        path: 'personal-medico',
        data: { pageTitle: 'historiacApp.personalMedico.home.title' },
        loadChildren: () => import('./personal-medico/personal-medico.module').then(m => m.PersonalMedicoModule),
      },
      {
        path: 'paciente',
        data: { pageTitle: 'historiacApp.paciente.home.title' },
        loadChildren: () => import('./paciente/paciente.module').then(m => m.PacienteModule),
      },
      {
        path: 'cita-medica',
        data: { pageTitle: 'historiacApp.citaMedica.home.title' },
        loadChildren: () => import('./cita-medica/cita-medica.module').then(m => m.CitaMedicaModule),
      },
      {
        path: 'consulta-medica',
        data: { pageTitle: 'historiacApp.consultaMedica.home.title' },
        loadChildren: () => import('./consulta-medica/consulta-medica.module').then(m => m.ConsultaMedicaModule),
      },
      {
        path: 'evaluacion-fisica',
        data: { pageTitle: 'historiacApp.evaluacionFisica.home.title' },
        loadChildren: () => import('./evaluacion-fisica/evaluacion-fisica.module').then(m => m.EvaluacionFisicaModule),
      },
      {
        path: 'medicacion',
        data: { pageTitle: 'historiacApp.medicacion.home.title' },
        loadChildren: () => import('./medicacion/medicacion.module').then(m => m.MedicacionModule),
      },
      {
        path: 'tratamiento',
        data: { pageTitle: 'historiacApp.tratamiento.home.title' },
        loadChildren: () => import('./tratamiento/tratamiento.module').then(m => m.TratamientoModule),
      },
      {
        path: 'estudio-medico',
        data: { pageTitle: 'historiacApp.estudioMedico.home.title' },
        loadChildren: () => import('./estudio-medico/estudio-medico.module').then(m => m.EstudioMedicoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
