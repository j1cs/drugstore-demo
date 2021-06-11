import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { StoreComponent } from './store.component';
import { CoreModule } from '@core';
import { SharedModule } from '@shared';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { GoogleMapsModule } from '@angular/google-maps';
import { NgxsModule } from '@ngxs/store';
import { StoreState } from '@app/store/store/store.state';

describe('StoreComponent', () => {
  let component: StoreComponent;
  let fixture: ComponentFixture<StoreComponent>;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        imports: [
          CoreModule,
          SharedModule,
          ReactiveFormsModule,
          GoogleMapsModule,
          NgxsFormPluginModule,
          GoogleMapsModule,
          TranslateModule.forRoot(),
          NgxsModule.forRoot([]),
        ],
        declarations: [StoreComponent],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(StoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('form invalid when empty', () => {
    expect(component.searchForm.valid).toBeFalsy();
  });
  it('fields validity', () => {
    const controls = component.searchForm.controls;
    Object.entries(controls).forEach(
      ([key, value]) => expect(value.valid).toBeFalsy()
    );
  });
  it('submitting a form', () => {
    expect(component.searchForm.valid).toBeFalsy();
    component.searchForm.controls.borough.setValue('recoleta');
    component.searchForm.controls.name.setValue('ahumada');
    expect(component.searchForm.valid).toBeTruthy();

  });
});
