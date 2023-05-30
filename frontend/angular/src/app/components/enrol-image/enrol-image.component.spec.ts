import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrolImageComponent } from './enrol-image.component';

describe('EnrolImageComponent', () => {
  let component: EnrolImageComponent;
  let fixture: ComponentFixture<EnrolImageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnrolImageComponent]
    });
    fixture = TestBed.createComponent(EnrolImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
