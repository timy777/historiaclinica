import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SalaMedicaService } from '../service/sala-medica.service';
import { ISalaMedica, SalaMedica } from '../sala-medica.model';

import { SalaMedicaUpdateComponent } from './sala-medica-update.component';

describe('SalaMedica Management Update Component', () => {
  let comp: SalaMedicaUpdateComponent;
  let fixture: ComponentFixture<SalaMedicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salaMedicaService: SalaMedicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SalaMedicaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SalaMedicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalaMedicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salaMedicaService = TestBed.inject(SalaMedicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salaMedica: ISalaMedica = { id: 456 };

      activatedRoute.data = of({ salaMedica });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(salaMedica));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalaMedica>>();
      const salaMedica = { id: 123 };
      jest.spyOn(salaMedicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salaMedica }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(salaMedicaService.update).toHaveBeenCalledWith(salaMedica);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalaMedica>>();
      const salaMedica = new SalaMedica();
      jest.spyOn(salaMedicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salaMedica }));
      saveSubject.complete();

      // THEN
      expect(salaMedicaService.create).toHaveBeenCalledWith(salaMedica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalaMedica>>();
      const salaMedica = { id: 123 };
      jest.spyOn(salaMedicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salaMedicaService.update).toHaveBeenCalledWith(salaMedica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
