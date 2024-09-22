import { Injectable } from '@angular/core';
import { UrlConstant } from '../../const/url-constant';
import { GlobalStatus } from '../../model/global/global-status';
import { HttpClient } from '@angular/common/http';
import { Observable, of, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GlobalService {
  private parameterMap = new Map<string, string>();

  public constructor(private httpClient: HttpClient) {}

  // Status & Parameter

  public getStatus(): Observable<GlobalStatus> {
    return this.httpClient.get<GlobalStatus>(UrlConstant.Global.STATUS);
  }

  public getParameterValue(parameterName: string): Observable<string | undefined> {
    if (this.parameterMap.has(parameterName)) {
      return of(this.parameterMap.get(parameterName));
    } else {
      return this.httpClient.get<string>(UrlConstant.Global.PARAMETERS + '/' + parameterName).pipe(
        map((parameterValue) => {
          this.parameterMap.set(parameterName, parameterValue);
          return parameterValue;
        })
      );
    }
  }
}
