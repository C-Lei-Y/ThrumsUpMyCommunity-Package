import { TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppMaterialModule } from './app-material.module';
import { UrlConstant } from './const/url-constant';
import { User } from './model/global/user';
import { GlobalStatus } from './model/global/global-status';
import { CountLikes } from './model/global/count-likes';
import { Observable } from 'rxjs';

let httpMock: HttpTestingController;

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule, AppMaterialModule],
      declarations: [AppComponent, PageNotFoundComponent],
      providers: [],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should create the app', async () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should have availableApp = true', async () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const app = fixture.debugElement.componentInstance;
    expect(app.availableApp).toEqual(true);

    await fixture.whenStable();
    expect(app.availableApp).toEqual(true);
  });

  it('should init properly', async () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    const compiled = fixture.debugElement.nativeElement;
    fixture.autoDetectChanges(true);

    expect(app.initApp).toEqual(false);
    (app.likes$ as Observable<number>).subscribe((countLikes) => {
      expect(countLikes).toEqual(12);
    });

    const mockRequestUser = httpMock.expectOne(UrlConstant.User.CURRENT_USER);
    mockRequestUser.flush({ id: 1, username: 'user', enabled: true, roleList: ['ROLE_USER'] } as User);
    const mockRequestStatus = httpMock.expectOne(UrlConstant.Global.STATUS);
    mockRequestStatus.flush({ status: 'true' } as GlobalStatus);
    const mockRequestLikeCount = httpMock.expectOne(UrlConstant.Global.LIKE_COUNT);
    mockRequestLikeCount.flush({ count: 12 } as CountLikes);
    httpMock.verify();

    await fixture.whenStable();
    await fixture.whenStable();
    expect(app.initApp).toEqual(true);
    expect(compiled.querySelector('h1').textContent).toContain('Post-It');
  });
});
