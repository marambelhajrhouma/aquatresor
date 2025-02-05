import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientSignInComponent } from './client-sign-in.component';

describe('ClientSignInComponent', () => {
  let component: ClientSignInComponent;
  let fixture: ComponentFixture<ClientSignInComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClientSignInComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientSignInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
