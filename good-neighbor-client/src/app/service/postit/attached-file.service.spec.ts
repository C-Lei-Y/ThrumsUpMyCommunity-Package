import { TestBed } from '@angular/core/testing';

import { AttachedFileService } from './attached-file.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AttachedFileService', () => {
  let service: AttachedFileService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(AttachedFileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
