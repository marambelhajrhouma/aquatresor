import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BassinComponent } from './bassin.component';

describe('BassinComponent', () => {
  let component: BassinComponent;
  let fixture: ComponentFixture<BassinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BassinComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BassinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
