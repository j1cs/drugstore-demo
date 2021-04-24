import { Store } from '@app/store/service/store';
import { Search } from '@app/store/service/search';

export class GetStores {
  static readonly type = '[STORE] Get';
}

export class GetStoresByBoroughAndName {
  static readonly type = '[STORE] Get by Borough and Name';
}


export class AddStore {
  static readonly type = '[STORE] Add';

  constructor(public payload: Store) {
  }
}

export class RemoveStore {
  static readonly type = '[STORE] Remove';

  constructor(public payload: string) {
  }
}
