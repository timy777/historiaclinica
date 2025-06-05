import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProcesoMedicoDetailComponent } from './proceso-medico-detail.component';

describe('ProcesoMedico Management Detail Component', () => {
  let comp: ProcesoMedicoDetailComponent;
  let fixture: ComponentFixture<ProcesoMedicoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcesoMedicoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ procesoMedico: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProcesoMedicoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProcesoMedicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load procesoMedico on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.procesoMedico).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
