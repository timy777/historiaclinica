import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TratamientoDetailComponent } from './tratamiento-detail.component';

describe('Tratamiento Management Detail Component', () => {
  let comp: TratamientoDetailComponent;
  let fixture: ComponentFixture<TratamientoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TratamientoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tratamiento: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TratamientoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TratamientoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tratamiento on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tratamiento).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
