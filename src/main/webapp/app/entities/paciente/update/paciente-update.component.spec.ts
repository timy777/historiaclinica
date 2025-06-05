import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PacienteService } from '../service/paciente.service';
import { IPaciente, Paciente } from '../paciente.model';

import { PacienteUpdateComponent } from './paciente-update.component';

describe('Paciente Management Update Component', () => {
  let comp: PacienteUpdateComponent;
  let fixture: ComponentFixture<PacienteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pacienteService: PacienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PacienteUpdateComponent],
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
      .overrideTemplate(PacienteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PacienteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pacienteService = TestBed.inject(PacienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paciente: IPaciente = { id: 456 };

      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paciente));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Paciente>>();
      const paciente = { id: 123 };
      jest.spyOn(pacienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paciente }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pacienteService.update).toHaveBeenCalledWith(paciente);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Paciente>>();
      const paciente = new Paciente();
      jest.spyOn(pacienteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paciente }));
      saveSubject.complete();

      // THEN
      expect(pacienteService.create).toHaveBeenCalledWith(paciente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Paciente>>();
      const paciente = { id: 123 };
      jest.spyOn(pacienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pacienteService.update).toHaveBeenCalledWith(paciente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
