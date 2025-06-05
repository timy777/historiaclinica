import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EvaluacionFisicaDetailComponent } from './evaluacion-fisica-detail.component';

describe('EvaluacionFisica Management Detail Component', () => {
  let comp: EvaluacionFisicaDetailComponent;
  let fixture: ComponentFixture<EvaluacionFisicaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EvaluacionFisicaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ evaluacionFisica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EvaluacionFisicaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EvaluacionFisicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load evaluacionFisica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.evaluacionFisica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
