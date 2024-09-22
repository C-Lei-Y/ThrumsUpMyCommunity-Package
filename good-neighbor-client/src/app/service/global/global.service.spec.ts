import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GlobalService } from './global.service';

describe('GlobalService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
  });

  it('should be created', inject([GlobalService], (service: GlobalService) => {
    expect(service).toBeTruthy();
  }));
});
