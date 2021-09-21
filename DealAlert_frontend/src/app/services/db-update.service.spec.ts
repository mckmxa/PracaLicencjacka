import { TestBed } from '@angular/core/testing';

import { DbUpdateService } from './db-update.service';

describe('DbUpdateService', () => {
  let service: DbUpdateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DbUpdateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
