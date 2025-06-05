import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalaMedica } from '../sala-medica.model';

@Component({
  selector: 'jhi-sala-medica-detail',
  templateUrl: './sala-medica-detail.component.html',
})
export class SalaMedicaDetailComponent implements OnInit {
  salaMedica: ISalaMedica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salaMedica }) => {
      this.salaMedica = salaMedica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
