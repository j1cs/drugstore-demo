import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Store } from '@app/store/service/store';
import { Form } from '@app/store/service/form';

const routes = {
  store: () => `/store/all`,
  storesByCommuneAndName: (borough: string, name:string) => `/store/${borough}/${name}`
};

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor(private httpClient: HttpClient) {
  }
  getStores(): Observable<Store[]> {
    return this.httpClient.get<Store[]>(routes.store());
  }
  getStoresByBoroughAndName(payload: Form): Observable<Store[]> {
    return this.httpClient.get<Store[]>(routes.storesByCommuneAndName(payload.borough, payload.name));
  }
}
