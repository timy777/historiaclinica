import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConsultaMedicaService } from '../service/consulta-medica.service';
import { IConsultaMedica, ConsultaMedica } from '../consulta-medica.model';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';
import { PersonalMedicoService } from 'app/entities/personal-medico/service/personal-medico.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';

import { ConsultaMedicaUpdateComponent } from './consulta-medica-update.component';

describe('ConsultaMedica Management Update Component', () => {
  let comp: ConsultaMedicaUpdateComponent;
  let fixture: ComponentFixture<ConsultaMedicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let consultaMedicaService: ConsultaMedicaService;
  let personalMedicoService: PersonalMedicoService;
  let pacienteService: PacienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConsultaMedicaUpdateComponent],
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
      .overrideTemplate(ConsultaMedicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConsultaMedicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    consultaMedicaService = TestBed.inject(ConsultaMedicaService);
    personalMedicoService = TestBed.inject(PersonalMedicoService);
    pacienteService = TestBed.inject(PacienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PersonalMedico query and add missing value', () => {
      const consultaMedica: IConsultaMedica = { id: 456 };
      const personalMedico: IPersonalMedico = { id: 65389 };
      consultaMedica.personalMedico = personalMedico;

      const personalMedicoCollection: IPersonalMedico[] = [{ id: 69964 }];
      jest.spyOn(personalMedicoService, 'query').mockReturnValue(of(new HttpResponse({ body: personalMedicoCollection })));
      const additionalPersonalMedicos = [personalMedico];
      const expectedCollection: IPersonalMedico[] = [...additionalPersonalMedicos, ...personalMedicoCollection];
      jest.spyOn(personalMedicoService, 'addPersonalMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consultaMedica });
      comp.ngOnInit();

      expect(personalMedicoService.query).toHaveBeenCalled();
      expect(personalMedicoService.addPersonalMedicoToCollectionIfMissing).toHaveBeenCalledWith(
        personalMedicoCollection,
        ...additionalPersonalMedicos
      );
      expect(comp.personalMedicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Paciente query and add missing value', () => {
      const consultaMedica: IConsultaMedica = { id: 456 };
      const paciente: IPaciente = { id: 13259 };
      consultaMedica.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 49824 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consultaMedica });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(pacienteCollection, ...additionalPacientes);
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const consultaMedica: IConsultaMedica = { id: 456 };
      const personalMedico: IPersonalMedico = { id: 52631 };
      consultaMedica.personalMedico = personalMedico;
      const paciente: IPaciente = { id: 50421 };
      consultaMedica.paciente = paciente;

      activatedRoute.data = of({ consultaMedica });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(consultaMedica));
      expect(comp.personalMedicosSharedCollection).toContain(personalMedico);
      expect(comp.pacientesSharedCollection).toContain(paciente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsultaMedica>>();
      const consultaMedica = { id: 123 };
      jest.spyOn(consultaMedicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consultaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consultaMedica }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(consultaMedicaService.update).toHaveBeenCalledWith(consultaMedica);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsultaMedica>>();
      const consultaMedica = new ConsultaMedica();
      jest.spyOn(consultaMedicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consultaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consultaMedica }));
      saveSubject.complete();

      // THEN
      expect(consultaMedicaService.create).toHaveBeenCalledWith(consultaMedica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsultaMedica>>();
      const consultaMedica = { id: 123 };
      jest.spyOn(consultaMedicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consultaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(consultaMedicaService.update).toHaveBeenCalledWith(consultaMedica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPersonalMedicoById', () => {
      it('Should return tracked PersonalMedico primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPersonalMedicoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPacienteById', () => {
      it('Should return tracked Paciente primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPacienteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
