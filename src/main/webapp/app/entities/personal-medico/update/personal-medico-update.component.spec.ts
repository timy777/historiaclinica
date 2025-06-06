import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonalMedicoService } from '../service/personal-medico.service';
import { IPersonalMedico, PersonalMedico } from '../personal-medico.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';

import { PersonalMedicoUpdateComponent } from './personal-medico-update.component';

describe('PersonalMedico Management Update Component', () => {
  let comp: PersonalMedicoUpdateComponent;
  let fixture: ComponentFixture<PersonalMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personalMedicoService: PersonalMedicoService;
  let pacienteService: PacienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonalMedicoUpdateComponent],
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
      .overrideTemplate(PersonalMedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonalMedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personalMedicoService = TestBed.inject(PersonalMedicoService);
    pacienteService = TestBed.inject(PacienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Paciente query and add missing value', () => {
      const personalMedico: IPersonalMedico = { id: 456 };
      const pacientes: IPaciente[] = [{ id: 56476 }];
      personalMedico.pacientes = pacientes;

      const pacienteCollection: IPaciente[] = [{ id: 54482 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [...pacientes];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personalMedico });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(pacienteCollection, ...additionalPacientes);
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const personalMedico: IPersonalMedico = { id: 456 };
      const pacientes: IPaciente = { id: 28522 };
      personalMedico.pacientes = [pacientes];

      activatedRoute.data = of({ personalMedico });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(personalMedico));
      expect(comp.pacientesSharedCollection).toContain(pacientes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonalMedico>>();
      const personalMedico = { id: 123 };
      jest.spyOn(personalMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personalMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personalMedico }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(personalMedicoService.update).toHaveBeenCalledWith(personalMedico);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonalMedico>>();
      const personalMedico = new PersonalMedico();
      jest.spyOn(personalMedicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personalMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personalMedico }));
      saveSubject.complete();

      // THEN
      expect(personalMedicoService.create).toHaveBeenCalledWith(personalMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonalMedico>>();
      const personalMedico = { id: 123 };
      jest.spyOn(personalMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personalMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personalMedicoService.update).toHaveBeenCalledWith(personalMedico);
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
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedPaciente', () => {
      it('Should return option if no Paciente is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedPaciente(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Paciente for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedPaciente(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Paciente is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedPaciente(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
