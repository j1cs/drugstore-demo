import { Action, Selector, State, StateContext } from '@ngxs/store';
import { User } from '@app/user/service/user';
import { Injectable } from '@angular/core';
import { UserService } from '@app/user/service/user.service';
import { AddUser, GetUser, GetUsers, RemoveUser, UpdateUser } from '@app/user/store/user.actions';
import { tap } from 'rxjs/operators';

export class UserFormModel {
  model: User;
  dirty: boolean;
  status: string;
  errors: object;
}

export class UserStateModel {
  users: User[];
  user: UserFormModel;
}

@State<UserStateModel>({
  name: 'users',
  defaults: {
    users: [],
    user: {
      model: undefined,
      dirty: false,
      status: '',
      errors: {},
    },
  },
})
@Injectable()
export class UserState {
  @Selector()
  static getUsers(state: UserStateModel) {
    return state.users;
  }

  @Selector()
  static getUser(state: UserStateModel) {
    return state.user;
  }

  @Selector()
  static setUser(state: UserStateModel) {
    return state.user;
  }
  constructor(private userService: UserService) {}

  @Action(GetUsers)
  getAll({ getState, setState }: StateContext<UserStateModel>) {
    return this.userService.all().pipe(
      tap((result) => {
        const state = getState();
        setState({
          ...state,
          users: result,
        });
      })
    );
  }

  // this should be used in show template
  @Action(GetUser)
  get({ getState, setState }: StateContext<UserStateModel>, { id }: GetUser) {
    return this.userService.get(id).pipe(
      tap((result) => {
        const state = getState();
        const userList = [...state.users];
        const index = userList.findIndex((item) => item.id === id);
        if (index) {
          userList[index] = result;
        } else {
          userList.push(result);
        }
        setState({
          ...state,
          users: userList,
        });
      })
    );
  }

  @Action(AddUser)
  add({ getState, patchState }: StateContext<UserStateModel>) {
    const payload = getState().user.model;
    return this.userService.create(payload).pipe(
      tap((result) => {
        const state = getState();
        patchState({
          users: [...state.users, result],
        });
      })
    );
  }

  @Action(UpdateUser)
  update({ getState, setState }: StateContext<UserStateModel>) {
    const payload = getState().user.model;
    console.log(payload);
    return this.userService.update(payload).pipe(
      tap((result) => {
        const state = getState();
        const userList = [...state.users];
        const index = userList.findIndex((item) => item.id === payload.id);
        userList[index] = result;
        setState({
          ...state,
          users: userList,
        });
      })
    );
  }

  @Action(RemoveUser)
  delete({ getState, setState }: StateContext<UserStateModel>, { id }: RemoveUser) {
    return this.userService.delete(id).pipe(
      tap(() => {
        const state = getState();
        const filteredArray = state.users.filter((item) => item.id !== id);
        setState({
          ...state,
          users: filteredArray,
        });
      })
    );
  }
}
