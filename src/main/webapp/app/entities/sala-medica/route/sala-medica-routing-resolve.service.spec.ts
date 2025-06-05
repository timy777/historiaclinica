import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISalaMedica, SalaMedica } from '../sala-medica.model';
import { SalaMedicaService } from '../service/sala-medica.service';

import { SalaMedicaRoutingResolveService } from './sala-medica-routing-resolve.service';

describe('SalaMedica routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SalaMedicaRoutingResolveService;
  let service: SalaMedicaService;
  let resultSalaMedica: ISalaMedica | undefined;

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
    routingResolveService = TestBed.inject(SalaMedicaRoutingResolveService);
    service = TestBed.inject(SalaMedicaService);
    resultSalaMedica = undefined;
  });

  describe('resolve', () => {
    it('should return ISalaMedica returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSalaMedica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSalaMedica).toEqual({ id: 123 });
    });

    it('should return new ISalaMedica if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSalaMedica = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSalaMedica).toEqual(new SalaMedica());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SalaMedica })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSalaMedica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSalaMedica).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
