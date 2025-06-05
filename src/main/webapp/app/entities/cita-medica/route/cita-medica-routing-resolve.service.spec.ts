import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICitaMedica, CitaMedica } from '../cita-medica.model';
import { CitaMedicaService } from '../service/cita-medica.service';

import { CitaMedicaRoutingResolveService } from './cita-medica-routing-resolve.service';

describe('CitaMedica routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CitaMedicaRoutingResolveService;
  let service: CitaMedicaService;
  let resultCitaMedica: ICitaMedica | undefined;

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
    routingResolveService = TestBed.inject(CitaMedicaRoutingResolveService);
    service = TestBed.inject(CitaMedicaService);
    resultCitaMedica = undefined;
  });

  describe('resolve', () => {
    it('should return ICitaMedica returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCitaMedica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCitaMedica).toEqual({ id: 123 });
    });

    it('should return new ICitaMedica if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCitaMedica = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCitaMedica).toEqual(new CitaMedica());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CitaMedica })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCitaMedica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCitaMedica).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
