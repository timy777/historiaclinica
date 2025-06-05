import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEstudioMedico, EstudioMedico } from '../estudio-medico.model';
import { EstudioMedicoService } from '../service/estudio-medico.service';

import { EstudioMedicoRoutingResolveService } from './estudio-medico-routing-resolve.service';

describe('EstudioMedico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EstudioMedicoRoutingResolveService;
  let service: EstudioMedicoService;
  let resultEstudioMedico: IEstudioMedico | undefined;

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
    routingResolveService = TestBed.inject(EstudioMedicoRoutingResolveService);
    service = TestBed.inject(EstudioMedicoService);
    resultEstudioMedico = undefined;
  });

  describe('resolve', () => {
    it('should return IEstudioMedico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstudioMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEstudioMedico).toEqual({ id: 123 });
    });

    it('should return new IEstudioMedico if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstudioMedico = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEstudioMedico).toEqual(new EstudioMedico());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EstudioMedico })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstudioMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEstudioMedico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
