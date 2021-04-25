import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Store } from '@app/store/service/store';
import { Search } from '@app/store/service/search';

const routes = {
  store: () => `/store/all`,
  storesByCommuneAndName: (borough: string, name: string) => `/store/${borough}/${name}`,
  storeNames: () => `/store/all/names`,
  boroughs: () => `/borough/all`,
};

@Injectable({
  providedIn: 'root',
})
export class StoreService {
  constructor(private httpClient: HttpClient) {}

  getStores(): Observable<Store[]> {
    return this.httpClient.get<Store[]>(routes.store());
  }

  getStoresByBoroughAndName(payload: Search): Observable<Store[]> {
    return this.httpClient.get<Store[]>(routes.storesByCommuneAndName(payload.borough, payload.name));
  }

  getStoreNames(): Observable<string[]> {
    return this.httpClient.get<string[]>(routes.storeNames());
  }

  getBorough(): Observable<string[]> {
    return this.httpClient.get<string[]>(routes.boroughs());
  }
}
