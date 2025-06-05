import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICitaMedica } from '../cita-medica.model';

@Component({
  selector: 'jhi-cita-medica-detail',
  templateUrl: './cita-medica-detail.component.html',
})
export class CitaMedicaDetailComponent implements OnInit {
  citaMedica: ICitaMedica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ citaMedica }) => {
      this.citaMedica = citaMedica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
