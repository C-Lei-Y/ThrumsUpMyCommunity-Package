import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { AuthService } from '../auth/auth.service';
import { GlobalInfoService } from './global-info.service';
import { AlertType } from 'src/app/const/alert-type';
import { GlobalConstant } from 'src/app/const/global-constant';

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {
  public constructor(private authService: AuthService, private globalInfoService: GlobalInfoService) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.globalInfoService.notifLoader(true);

    return next.handle(request).pipe(
      tap(
        (event) => {
          // do nothing when success
          this.globalInfoService.notifLoader(false);
        },
        (error) => {
          this.globalInfoService.notifLoader(false);
          let errMsg;

          if (error instanceof HttpErrorResponse) {
            if (error.status === 401) {
              if (!request.url.endsWith('/login')) {
                this.authService.redirectToLogin(null);
              }
              this.globalInfoService.showAlert(
                AlertType.WARNING,
                $localize`:@@global.accessUnauthorized:Access Unauthorized. Please sign in.`
              );
              return;
            } else if (error.status === 403) {
              this.globalInfoService.showAlert(
                AlertType.DANGER,
                $localize`:@@global.accessForbidden:Access Forbidden !`
              );
              return;
            } else if (error.status !== 500 && error.status && error.error.error) {
              this.globalInfoService.showAlert(
                AlertType.DANGER,
                error.error.error + ' : ' + error.error.message,
                GlobalConstant.Display.NOTIFICATION_DELAY * 3
              );
              return;
            }

            if (error.error.error) {
              // Message from json for server error
              errMsg = error.error.message;
            } else {
              // Message from status
              errMsg = error.status + ' ' + error.statusText;
            }
          } else {
            // Network error ?
            errMsg = request.url + ' - ' + error;
          }

          this.globalInfoService.showAlert(
            AlertType.DANGER,
            $localize`:@@global.technicalError:Technical Error` + ' : ' + errMsg,
            GlobalConstant.Display.NOTIFICATION_DELAY * 5
          );
        }
      )
    );
  }
}
