import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConsultaMedicaDetailComponent } from './consulta-medica-detail.component';

describe('ConsultaMedica Management Detail Component', () => {
  let comp: ConsultaMedicaDetailComponent;
  let fixture: ComponentFixture<ConsultaMedicaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConsultaMedicaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ consultaMedica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ConsultaMedicaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConsultaMedicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load consultaMedica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.consultaMedica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
