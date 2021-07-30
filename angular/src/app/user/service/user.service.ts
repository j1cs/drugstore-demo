import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '@app/user/service/user';

const routes = {
  users: () => `/nestjs/user`,
  user: (id: number) => `/nestjs/user/${id}`,
};

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private httpClient: HttpClient) {}

  all(): Observable<User[]> {
    return this.httpClient.get<User[]>(routes.users());
  }

  get(id: number): Observable<User> {
    return this.httpClient.get<User>(routes.user(id));
  }

  create(user: User): Observable<User> {
    return this.httpClient.post<User>(routes.users(), user);
  }

  update(user: User): Observable<User> {
    return this.httpClient.patch<User>(routes.user(user.id), user);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(routes.user(id));
  }
}
