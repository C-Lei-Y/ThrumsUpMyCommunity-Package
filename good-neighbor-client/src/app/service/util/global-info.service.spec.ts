import { TestBed, inject } from '@angular/core/testing';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { GlobalInfoService } from './global-info.service';

describe('GlobalInfoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GlobalInfoService, MatSnackBar],
      imports: [MatSnackBarModule],
    });
  });

  it('should be created', inject([GlobalInfoService], (service: GlobalInfoService) => {
    expect(service).toBeTruthy();
  }));
});
