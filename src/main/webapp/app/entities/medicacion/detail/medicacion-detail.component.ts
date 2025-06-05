import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicacion } from '../medicacion.model';

@Component({
  selector: 'jhi-medicacion-detail',
  templateUrl: './medicacion-detail.component.html',
})
export class MedicacionDetailComponent implements OnInit {
  medicacion: IMedicacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicacion }) => {
      this.medicacion = medicacion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
