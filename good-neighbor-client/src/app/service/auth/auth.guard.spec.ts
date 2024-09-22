import { TestBed, async, inject } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import { AuthService } from './auth.service';

describe('AuthGuard', () => {
  beforeEach(async () => {
    const authSpy = jasmine.createSpyObj('AuthService', ['redirectToLogin']);

    await TestBed.configureTestingModule({
      providers: [{ provide: AuthService, useValue: authSpy }],
    });
  });

  it('should ...', inject([AuthGuard], (guard: AuthGuard) => {
    expect(guard).toBeTruthy();
  }));
});
