import { TestBed } from '@angular/core/testing';

import { LikeService } from './like.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

let likeService: LikeService;
describe('LikeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });

    likeService = TestBed.inject(LikeService);
  });

  it('should be created', () => {
    const service: LikeService = TestBed.inject(LikeService);
    expect(service).toBeTruthy();
  });
});
