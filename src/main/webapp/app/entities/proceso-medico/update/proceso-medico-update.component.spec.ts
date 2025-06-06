import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProcesoMedicoService } from '../service/proceso-medico.service';
import { IProcesoMedico, ProcesoMedico } from '../proceso-medico.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';
import { PersonalMedicoService } from 'app/entities/personal-medico/service/personal-medico.service';
import { ISalaMedica } from 'app/entities/sala-medica/sala-medica.model';
import { SalaMedicaService } from 'app/entities/sala-medica/service/sala-medica.service';

import { ProcesoMedicoUpdateComponent } from './proceso-medico-update.component';

describe('ProcesoMedico Management Update Component', () => {
  let comp: ProcesoMedicoUpdateComponent;
  let fixture: ComponentFixture<ProcesoMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let procesoMedicoService: ProcesoMedicoService;
  let pacienteService: PacienteService;
  let personalMedicoService: PersonalMedicoService;
  let salaMedicaService: SalaMedicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProcesoMedicoUpdateComponent],
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
      .overrideTemplate(ProcesoMedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProcesoMedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    procesoMedicoService = TestBed.inject(ProcesoMedicoService);
    pacienteService = TestBed.inject(PacienteService);
    personalMedicoService = TestBed.inject(PersonalMedicoService);
    salaMedicaService = TestBed.inject(SalaMedicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Paciente query and add missing value', () => {
      const procesoMedico: IProcesoMedico = { id: 456 };
      const paciente: IPaciente = { id: 84755 };
      procesoMedico.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 14171 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ procesoMedico });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(pacienteCollection, ...additionalPacientes);
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PersonalMedico query and add missing value', () => {
      const procesoMedico: IProcesoMedico = { id: 456 };
      const personalMedico: IPersonalMedico = { id: 28773 };
      procesoMedico.personalMedico = personalMedico;

      const personalMedicoCollection: IPersonalMedico[] = [{ id: 69404 }];
      jest.spyOn(personalMedicoService, 'query').mockReturnValue(of(new HttpResponse({ body: personalMedicoCollection })));
      const additionalPersonalMedicos = [personalMedico];
      const expectedCollection: IPersonalMedico[] = [...additionalPersonalMedicos, ...personalMedicoCollection];
      jest.spyOn(personalMedicoService, 'addPersonalMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ procesoMedico });
      comp.ngOnInit();

      expect(personalMedicoService.query).toHaveBeenCalled();
      expect(personalMedicoService.addPersonalMedicoToCollectionIfMissing).toHaveBeenCalledWith(
        personalMedicoCollection,
        ...additionalPersonalMedicos
      );
      expect(comp.personalMedicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SalaMedica query and add missing value', () => {
      const procesoMedico: IProcesoMedico = { id: 456 };
      const salaMedica: ISalaMedica = { id: 34439 };
      procesoMedico.salaMedica = salaMedica;

      const salaMedicaCollection: ISalaMedica[] = [{ id: 81552 }];
      jest.spyOn(salaMedicaService, 'query').mockReturnValue(of(new HttpResponse({ body: salaMedicaCollection })));
      const additionalSalaMedicas = [salaMedica];
      const expectedCollection: ISalaMedica[] = [...additionalSalaMedicas, ...salaMedicaCollection];
      jest.spyOn(salaMedicaService, 'addSalaMedicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ procesoMedico });
      comp.ngOnInit();

      expect(salaMedicaService.query).toHaveBeenCalled();
      expect(salaMedicaService.addSalaMedicaToCollectionIfMissing).toHaveBeenCalledWith(salaMedicaCollection, ...additionalSalaMedicas);
      expect(comp.salaMedicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const procesoMedico: IProcesoMedico = { id: 456 };
      const paciente: IPaciente = { id: 69626 };
      procesoMedico.paciente = paciente;
      const personalMedico: IPersonalMedico = { id: 82975 };
      procesoMedico.personalMedico = personalMedico;
      const salaMedica: ISalaMedica = { id: 12329 };
      procesoMedico.salaMedica = salaMedica;

      activatedRoute.data = of({ procesoMedico });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(procesoMedico));
      expect(comp.pacientesSharedCollection).toContain(paciente);
      expect(comp.personalMedicosSharedCollection).toContain(personalMedico);
      expect(comp.salaMedicasSharedCollection).toContain(salaMedica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProcesoMedico>>();
      const procesoMedico = { id: 123 };
      jest.spyOn(procesoMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procesoMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: procesoMedico }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(procesoMedicoService.update).toHaveBeenCalledWith(procesoMedico);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProcesoMedico>>();
      const procesoMedico = new ProcesoMedico();
      jest.spyOn(procesoMedicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procesoMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: procesoMedico }));
      saveSubject.complete();

      // THEN
      expect(procesoMedicoService.create).toHaveBeenCalledWith(procesoMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProcesoMedico>>();
      const procesoMedico = { id: 123 };
      jest.spyOn(procesoMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procesoMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(procesoMedicoService.update).toHaveBeenCalledWith(procesoMedico);
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

    describe('trackSalaMedicaById', () => {
      it('Should return tracked SalaMedica primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSalaMedicaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
