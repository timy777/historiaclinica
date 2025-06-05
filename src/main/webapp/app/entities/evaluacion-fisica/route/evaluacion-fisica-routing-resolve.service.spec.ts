import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEvaluacionFisica, EvaluacionFisica } from '../evaluacion-fisica.model';
import { EvaluacionFisicaService } from '../service/evaluacion-fisica.service';

import { EvaluacionFisicaRoutingResolveService } from './evaluacion-fisica-routing-resolve.service';

describe('EvaluacionFisica routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EvaluacionFisicaRoutingResolveService;
  let service: EvaluacionFisicaService;
  let resultEvaluacionFisica: IEvaluacionFisica | undefined;

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
    routingResolveService = TestBed.inject(EvaluacionFisicaRoutingResolveService);
    service = TestBed.inject(EvaluacionFisicaService);
    resultEvaluacionFisica = undefined;
  });

  describe('resolve', () => {
    it('should return IEvaluacionFisica returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEvaluacionFisica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEvaluacionFisica).toEqual({ id: 123 });
    });

    it('should return new IEvaluacionFisica if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEvaluacionFisica = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEvaluacionFisica).toEqual(new EvaluacionFisica());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EvaluacionFisica })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEvaluacionFisica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEvaluacionFisica).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
