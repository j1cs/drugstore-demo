import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Store } from './store';

@Injectable({ providedIn: 'root' })
export class AppService {
  private storesUrl = 'api/stores';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getStores(): Observable<Store[]> {
    return this.http.get<Store[]>(this.storesUrl)
      .pipe(
        tap(_ => this.log('fetched stores')),
        catchError(this.handleError<Store[]>('getStores', []))
      );
  }

  getStoresByCommune(commune: string): Observable<Store[]> {
    const url = `${this.storesUrl}/commune/${commune}`;
    return this.http.get<Store[]>(url).pipe(
      tap(_ => this.log(`fetched stores commune=${commune}`)),
      catchError(this.handleError<Store[]>(`getStoresByCommune commune=${commune}`))
    );
  }

  getStoresByName(name: string): Observable<Store[]> {
    const url = `${this.storesUrl}/name/${name}`;
    return this.http.get<Store[]>(url).pipe(
      tap(_ => this.log(`fetched stores name=${name}`)),
      catchError(this.handleError<Store[]>(`getStoresByName name=${name}`))
    );
  }

  getStoresByCommuneAndName(commune: string, name: string): Observable<Store[]> {
    const url = `${this.storesUrl}/${commune}/${name}`;
    return this.http.get<Store[]>(url).pipe(
      tap(_ => this.log(`fetched stores commune=${commune}, name=${name}`)),
      catchError(this.handleError<Store[]>(`getStoresByCommuneAndName commune=${commune}, name=${name}`))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    console.log(message);
  }
}
