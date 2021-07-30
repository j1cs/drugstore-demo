import { User } from '@app/user/service/user';

export class GetUsers {
  static readonly type = '[USER] All';
}

export class GetUser {
  static readonly type = '[USER] Get';

  constructor(public id: number) {}
}

export class AddUser {
  static readonly type = '[USER] Add';
}

export class UpdateUser {
  static readonly type = '[USER] Update';
}

export class RemoveUser {
  static readonly type = '[USER] Remove';

  constructor(public id: number) {}
}
