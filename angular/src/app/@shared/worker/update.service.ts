import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { SwUpdate } from '@angular/service-worker';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class UpdateService {
  constructor(private swUpdate: SwUpdate, private modalService: NgbModal, private translateService: TranslateService) {}

  check() {
    this.swUpdate.available.subscribe((evt) => {
      if (confirm(this.translateService.instant('Update Available'))) {
        window.location.reload();
      }
    });
  }
}
