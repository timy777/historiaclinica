import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CitaMedicaService } from '../service/cita-medica.service';
import { ICitaMedica, CitaMedica } from '../cita-medica.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';
import { PersonalMedicoService } from 'app/entities/personal-medico/service/personal-medico.service';

import { CitaMedicaUpdateComponent } from './cita-medica-update.component';

describe('CitaMedica Management Update Component', () => {
  let comp: CitaMedicaUpdateComponent;
  let fixture: ComponentFixture<CitaMedicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let citaMedicaService: CitaMedicaService;
  let pacienteService: PacienteService;
  let personalMedicoService: PersonalMedicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CitaMedicaUpdateComponent],
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
      .overrideTemplate(CitaMedicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CitaMedicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    citaMedicaService = TestBed.inject(CitaMedicaService);
    pacienteService = TestBed.inject(PacienteService);
    personalMedicoService = TestBed.inject(PersonalMedicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Paciente query and add missing value', () => {
      const citaMedica: ICitaMedica = { id: 456 };
      const paciente: IPaciente = { id: 29639 };
      citaMedica.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 22379 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ citaMedica });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(pacienteCollection, ...additionalPacientes);
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PersonalMedico query and add missing value', () => {
      const citaMedica: ICitaMedica = { id: 456 };
      const personalMedico: IPersonalMedico = { id: 96723 };
      citaMedica.personalMedico = personalMedico;

      const personalMedicoCollection: IPersonalMedico[] = [{ id: 80137 }];
      jest.spyOn(personalMedicoService, 'query').mockReturnValue(of(new HttpResponse({ body: personalMedicoCollection })));
      const additionalPersonalMedicos = [personalMedico];
      const expectedCollection: IPersonalMedico[] = [...additionalPersonalMedicos, ...personalMedicoCollection];
      jest.spyOn(personalMedicoService, 'addPersonalMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ citaMedica });
      comp.ngOnInit();

      expect(personalMedicoService.query).toHaveBeenCalled();
      expect(personalMedicoService.addPersonalMedicoToCollectionIfMissing).toHaveBeenCalledWith(
        personalMedicoCollection,
        ...additionalPersonalMedicos
      );
      expect(comp.personalMedicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const citaMedica: ICitaMedica = { id: 456 };
      const paciente: IPaciente = { id: 44100 };
      citaMedica.paciente = paciente;
      const personalMedico: IPersonalMedico = { id: 79225 };
      citaMedica.personalMedico = personalMedico;

      activatedRoute.data = of({ citaMedica });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(citaMedica));
      expect(comp.pacientesSharedCollection).toContain(paciente);
      expect(comp.personalMedicosSharedCollection).toContain(personalMedico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CitaMedica>>();
      const citaMedica = { id: 123 };
      jest.spyOn(citaMedicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ citaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: citaMedica }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(citaMedicaService.update).toHaveBeenCalledWith(citaMedica);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CitaMedica>>();
      const citaMedica = new CitaMedica();
      jest.spyOn(citaMedicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ citaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: citaMedica }));
      saveSubject.complete();

      // THEN
      expect(citaMedicaService.create).toHaveBeenCalledWith(citaMedica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CitaMedica>>();
      const citaMedica = { id: 123 };
      jest.spyOn(citaMedicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ citaMedica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(citaMedicaService.update).toHaveBeenCalledWith(citaMedica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPacienteById', () => {
      it('Should return tracked Paciente primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPacienteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPersonalMedicoById', () => {
      it('Should return tracked PersonalMedico primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPersonalMedicoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
