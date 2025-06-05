import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SalaMedicaDetailComponent } from './sala-medica-detail.component';

describe('SalaMedica Management Detail Component', () => {
  let comp: SalaMedicaDetailComponent;
  let fixture: ComponentFixture<SalaMedicaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SalaMedicaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ salaMedica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SalaMedicaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalaMedicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salaMedica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.salaMedica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
