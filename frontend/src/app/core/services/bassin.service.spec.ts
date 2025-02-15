import { TestBed } from '@angular/core/testing';

import { BassinService } from './bassin.service';

describe('BassinService', () => {
  let service: BassinService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BassinService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
