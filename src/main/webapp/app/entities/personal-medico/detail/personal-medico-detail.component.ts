import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonalMedico } from '../personal-medico.model';

@Component({
  selector: 'jhi-personal-medico-detail',
  templateUrl: './personal-medico-detail.component.html',
})
export class PersonalMedicoDetailComponent implements OnInit {
  personalMedico: IPersonalMedico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personalMedico }) => {
      this.personalMedico = personalMedico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
