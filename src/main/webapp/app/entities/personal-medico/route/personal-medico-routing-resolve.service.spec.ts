import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPersonalMedico, PersonalMedico } from '../personal-medico.model';
import { PersonalMedicoService } from '../service/personal-medico.service';

import { PersonalMedicoRoutingResolveService } from './personal-medico-routing-resolve.service';

describe('PersonalMedico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PersonalMedicoRoutingResolveService;
  let service: PersonalMedicoService;
  let resultPersonalMedico: IPersonalMedico | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PersonalMedicoRoutingResolveService);
    service = TestBed.inject(PersonalMedicoService);
    resultPersonalMedico = undefined;
  });

  describe('resolve', () => {
    it('should return IPersonalMedico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPersonalMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPersonalMedico).toEqual({ id: 123 });
    });

    it('should return new IPersonalMedico if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPersonalMedico = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPersonalMedico).toEqual(new PersonalMedico());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PersonalMedico })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPersonalMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPersonalMedico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
