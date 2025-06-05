import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluacionFisica } from '../evaluacion-fisica.model';

@Component({
  selector: 'jhi-evaluacion-fisica-detail',
  templateUrl: './evaluacion-fisica-detail.component.html',
})
export class EvaluacionFisicaDetailComponent implements OnInit {
  evaluacionFisica: IEvaluacionFisica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluacionFisica }) => {
      this.evaluacionFisica = evaluacionFisica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
