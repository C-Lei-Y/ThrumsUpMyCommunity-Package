import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ReplaySubject, Observable, map, catchError, of, first } from 'rxjs';
import { User } from '../../model/global/user';
import { UserService } from '../global/user.service';
import { UrlConstant } from '../../const/url-constant';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userSubject = new ReplaySubject<User | null>(1);

  private routeBeforeLogin: ActivatedRouteSnapshot | null = null;

  public constructor(private router: Router, private httpClient: HttpClient, private userService: UserService) {}

  public getCurrentUser(): Observable<User | null> {
    return this.userSubject.asObservable();
  }

  public getRefreshedCurrentUser(): Observable<User> {
    return this.userService.getCurrentUser().pipe(
      map((user) => {
        this.userSubject.next(user);
        return user;
      })
    );
  }

  public userHasRoles(roleList: Array<string>): Observable<boolean> {
    return this.getCurrentUser().pipe(
      first(),
      map((user) => {
        if (!user?.roleList) {
          return false;
        } else {
          return !roleList || roleList.every((role) => user.roleList && user.roleList.includes(role));
        }
      })
    );
  }

  public redirectToLogin(oldRoute: ActivatedRouteSnapshot | null): void {
    this.routeBeforeLogin = oldRoute;
    this.router.navigate(['/login']);
  }

  public login(username: string, password: string): Observable<boolean> {
    const loginFormData = new FormData();
    loginFormData.append('username', username);
    loginFormData.append('password', password);

    return this.httpClient.post<User>(UrlConstant.LOGIN, loginFormData).pipe(
      map((user) => {
        this.userSubject.next(user);
        if (user && this.routeBeforeLogin && this.routeBeforeLogin.routeConfig) {
          this.router.navigate([this.routeBeforeLogin.routeConfig.path]);
        }
        return !!user;
      }),
      catchError(() => of(false))
    );
  }

  public logout(): Observable<void> {
    this.userSubject.next(null);
    return this.httpClient.post<void>(UrlConstant.LOGOUT, null).pipe(catchError(() => of()));
  }
}
