import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcesoMedico } from '../proceso-medico.model';

@Component({
  selector: 'jhi-proceso-medico-detail',
  templateUrl: './proceso-medico-detail.component.html',
})
export class ProcesoMedicoDetailComponent implements OnInit {
  procesoMedico: IProcesoMedico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ procesoMedico }) => {
      this.procesoMedico = procesoMedico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
