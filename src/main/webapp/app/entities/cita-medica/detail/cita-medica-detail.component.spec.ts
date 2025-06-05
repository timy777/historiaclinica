import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CitaMedicaDetailComponent } from './cita-medica-detail.component';

describe('CitaMedica Management Detail Component', () => {
  let comp: CitaMedicaDetailComponent;
  let fixture: ComponentFixture<CitaMedicaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CitaMedicaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ citaMedica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CitaMedicaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CitaMedicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load citaMedica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.citaMedica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
