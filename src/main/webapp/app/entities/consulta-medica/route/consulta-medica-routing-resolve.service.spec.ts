import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IConsultaMedica, ConsultaMedica } from '../consulta-medica.model';
import { ConsultaMedicaService } from '../service/consulta-medica.service';

import { ConsultaMedicaRoutingResolveService } from './consulta-medica-routing-resolve.service';

describe('ConsultaMedica routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ConsultaMedicaRoutingResolveService;
  let service: ConsultaMedicaService;
  let resultConsultaMedica: IConsultaMedica | undefined;

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
    routingResolveService = TestBed.inject(ConsultaMedicaRoutingResolveService);
    service = TestBed.inject(ConsultaMedicaService);
    resultConsultaMedica = undefined;
  });

  describe('resolve', () => {
    it('should return IConsultaMedica returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConsultaMedica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultConsultaMedica).toEqual({ id: 123 });
    });

    it('should return new IConsultaMedica if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConsultaMedica = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultConsultaMedica).toEqual(new ConsultaMedica());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ConsultaMedica })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConsultaMedica = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultConsultaMedica).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
