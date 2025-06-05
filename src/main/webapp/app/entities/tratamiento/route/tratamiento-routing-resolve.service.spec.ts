import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITratamiento, Tratamiento } from '../tratamiento.model';
import { TratamientoService } from '../service/tratamiento.service';

import { TratamientoRoutingResolveService } from './tratamiento-routing-resolve.service';

describe('Tratamiento routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TratamientoRoutingResolveService;
  let service: TratamientoService;
  let resultTratamiento: ITratamiento | undefined;

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
    routingResolveService = TestBed.inject(TratamientoRoutingResolveService);
    service = TestBed.inject(TratamientoService);
    resultTratamiento = undefined;
  });

  describe('resolve', () => {
    it('should return ITratamiento returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTratamiento = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTratamiento).toEqual({ id: 123 });
    });

    it('should return new ITratamiento if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTratamiento = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTratamiento).toEqual(new Tratamiento());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Tratamiento })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTratamiento = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTratamiento).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
