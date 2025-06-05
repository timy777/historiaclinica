import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstudioMedico } from '../estudio-medico.model';

@Component({
  selector: 'jhi-estudio-medico-detail',
  templateUrl: './estudio-medico-detail.component.html',
})
export class EstudioMedicoDetailComponent implements OnInit {
  estudioMedico: IEstudioMedico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estudioMedico }) => {
      this.estudioMedico = estudioMedico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
