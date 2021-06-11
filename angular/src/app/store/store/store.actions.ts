import { Store } from '@app/store/service/store';

export class GetStores {
  static readonly type = '[STORE] Get';
}

export class GetStoreNames {
  static readonly type = '[STORE] Get Names';
}

export class GetBoroughs {
  static readonly type = '[STORE] Get Borough';
}

export class GetStoresByBoroughAndName {
  static readonly type = '[STORE] Get by Borough and Name';
}

export class AddStore {
  static readonly type = '[STORE] Add';

  constructor(public payload: Store) {}
}

export class RemoveStore {
  static readonly type = '[STORE] Remove';

  constructor(public payload: string) {}
}
