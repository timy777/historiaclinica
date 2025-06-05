import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EstudioMedicoDetailComponent } from './estudio-medico-detail.component';

describe('EstudioMedico Management Detail Component', () => {
  let comp: EstudioMedicoDetailComponent;
  let fixture: ComponentFixture<EstudioMedicoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstudioMedicoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ estudioMedico: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EstudioMedicoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EstudioMedicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load estudioMedico on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.estudioMedico).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
