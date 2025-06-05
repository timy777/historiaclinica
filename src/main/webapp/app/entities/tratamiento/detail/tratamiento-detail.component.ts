import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITratamiento } from '../tratamiento.model';

@Component({
  selector: 'jhi-tratamiento-detail',
  templateUrl: './tratamiento-detail.component.html',
})
export class TratamientoDetailComponent implements OnInit {
  tratamiento: ITratamiento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tratamiento }) => {
      this.tratamiento = tratamiento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
