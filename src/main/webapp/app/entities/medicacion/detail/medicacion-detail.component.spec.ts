import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MedicacionDetailComponent } from './medicacion-detail.component';

describe('Medicacion Management Detail Component', () => {
  let comp: MedicacionDetailComponent;
  let fixture: ComponentFixture<MedicacionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MedicacionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ medicacion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MedicacionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MedicacionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load medicacion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.medicacion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
