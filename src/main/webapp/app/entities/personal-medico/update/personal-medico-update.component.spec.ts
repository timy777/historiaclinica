import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonalMedicoService } from '../service/personal-medico.service';
import { IPersonalMedico, PersonalMedico } from '../personal-medico.model';

import { PersonalMedicoUpdateComponent } from './personal-medico-update.component';

describe('PersonalMedico Management Update Component', () => {
  let comp: PersonalMedicoUpdateComponent;
  let fixture: ComponentFixture<PersonalMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personalMedicoService: PersonalMedicoService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const personalMedico: IPersonalMedico = { id: 456 };

      activatedRoute.data = of({ personalMedico });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(personalMedico));
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
});
