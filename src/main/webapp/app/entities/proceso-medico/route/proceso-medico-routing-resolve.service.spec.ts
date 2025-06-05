import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProcesoMedico, ProcesoMedico } from '../proceso-medico.model';
import { ProcesoMedicoService } from '../service/proceso-medico.service';

import { ProcesoMedicoRoutingResolveService } from './proceso-medico-routing-resolve.service';

describe('ProcesoMedico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProcesoMedicoRoutingResolveService;
  let service: ProcesoMedicoService;
  let resultProcesoMedico: IProcesoMedico | undefined;

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
    routingResolveService = TestBed.inject(ProcesoMedicoRoutingResolveService);
    service = TestBed.inject(ProcesoMedicoService);
    resultProcesoMedico = undefined;
  });

  describe('resolve', () => {
    it('should return IProcesoMedico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProcesoMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProcesoMedico).toEqual({ id: 123 });
    });

    it('should return new IProcesoMedico if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProcesoMedico = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProcesoMedico).toEqual(new ProcesoMedico());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProcesoMedico })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProcesoMedico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProcesoMedico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
