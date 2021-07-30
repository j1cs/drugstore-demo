import { TestBed } from '@angular/core/testing';

import { StoreService } from './store.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CoreModule } from '@core';
import { Type } from '@angular/core';
import { Store } from '@app/store/service/store';

describe('StoreService', () => {
  let service: StoreService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CoreModule, HttpClientTestingModule],
      providers: [StoreService],
    });
    service = TestBed.inject(StoreService);
    httpMock = TestBed.inject(HttpTestingController as Type<HttpTestingController>);
  });

  afterEach(() => {
    httpMock.verify();
  });
  describe('getStores', () => {
    it('should return all stores', () => {
      const mockStore: Store[] = [
        {
          date: [2021, 4, 26],
          id: 1,
          name: 'store',
          boroughName: 'boroughName',
          location: 'location',
          address: 'address',
          openingHours: [10, 30],
          closingHours: [19, 30],
          phone: '+56111111111',
          latitude: -33.0,
          longitude: -70.0,
          openingDay: 'monday',
          region: 7,
          borough: 122,
        },
      ];
      const storesSubscription = service.getStores();
      storesSubscription.subscribe((stores: Store[]) => {
        expect(stores).toEqual(mockStore);
      });
      httpMock.expectOne({}).flush(mockStore);
    });
  });
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
