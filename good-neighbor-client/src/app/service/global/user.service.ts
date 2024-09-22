import { Injectable } from '@angular/core';
import { User } from '../../model/global/user';
import { UrlConstant } from '../../const/url-constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public constructor(private httpClient: HttpClient) {}

  public getCurrentUser(): Observable<User> {
    return this.httpClient.get<User>(UrlConstant.User.CURRENT_USER);
  }

  public getUserList(): Observable<Array<User>> {
    return this.httpClient.get<Array<User>>(UrlConstant.User.USERS);
  }

  public createUser(user: User): Observable<User> {
    return this.httpClient.post<User>(UrlConstant.User.USERS, user);
  }

  public updateUser(user: User): Observable<User> {
    return this.httpClient.patch<User>(UrlConstant.User.USERS + '/' + user.id, user);
  }

  public deleteUser(userId: number): Observable<void> {
    return this.httpClient.delete<void>(UrlConstant.User.USERS + '/' + userId);
  }

  public getRoleList(): Observable<Array<string>> {
    return this.httpClient.get<Array<string>>(UrlConstant.User.ROLES);
  }
}
