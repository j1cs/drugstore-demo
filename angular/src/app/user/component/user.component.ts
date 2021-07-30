import { Component, OnInit } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { ActionsExecuting, actionsExecuting } from '@ngxs-labs/actions-executing';
import { AddUser, GetUser, GetUsers, RemoveUser, UpdateUser } from '@app/user/store/user.actions';
import { Observable } from 'rxjs';
import { User } from '@app/user/service/user';
import { UserState } from '@app/user/store/user.state';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
})
export class UserComponent implements OnInit {
  @Select(actionsExecuting([GetUser, GetUsers, AddUser, UpdateUser, RemoveUser]))
  isLoading$: Observable<ActionsExecuting>;
  @Select(UserState.getUsers) users$: Observable<User>;

  userForm = this.form.group({
    id: [],
    name: ['', Validators.required],
    age: ['', Validators.required],
  });

  editing = false;

  constructor(private store: Store, private form: FormBuilder) {}

  ngOnInit(): void {
    this.store.dispatch([new GetUsers()]);
  }

  onSubmit(): void {
    if (!this.editing) {
      this.store.dispatch(new AddUser()).subscribe(() => {
        this.clearForm();
      });
    } else {
      this.store.dispatch(new UpdateUser()).subscribe(() => {
        this.clearForm();
      });
    }
  }

  clearForm() {
    this.userForm.reset();
  }
  edit(user: User) {
    this.editing = true;
    console.log(user);
    this.userForm.patchValue({
      id: user.id,
      name: user.name,
      age: user.age,
    });
  }

  delete(id: number) {
    this.store.dispatch(new RemoveUser(id));
  }
}
