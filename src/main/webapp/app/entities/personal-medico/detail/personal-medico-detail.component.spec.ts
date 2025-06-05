import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonalMedicoDetailComponent } from './personal-medico-detail.component';

describe('PersonalMedico Management Detail Component', () => {
  let comp: PersonalMedicoDetailComponent;
  let fixture: ComponentFixture<PersonalMedicoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonalMedicoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personalMedico: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PersonalMedicoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonalMedicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personalMedico on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personalMedico).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
