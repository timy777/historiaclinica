import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConsultaMedica } from '../consulta-medica.model';

@Component({
  selector: 'jhi-consulta-medica-detail',
  templateUrl: './consulta-medica-detail.component.html',
})
export class ConsultaMedicaDetailComponent implements OnInit {
  consultaMedica: IConsultaMedica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consultaMedica }) => {
      this.consultaMedica = consultaMedica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
