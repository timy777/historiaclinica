import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProcesoMedicoService } from '../service/proceso-medico.service';
import { IProcesoMedico, ProcesoMedico } from '../proceso-medico.model';

import { ProcesoMedicoUpdateComponent } from './proceso-medico-update.component';

describe('ProcesoMedico Management Update Component', () => {
  let comp: ProcesoMedicoUpdateComponent;
  let fixture: ComponentFixture<ProcesoMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let procesoMedicoService: ProcesoMedicoService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const procesoMedico: IProcesoMedico = { id: 456 };

      activatedRoute.data = of({ procesoMedico });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(procesoMedico));
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
});
