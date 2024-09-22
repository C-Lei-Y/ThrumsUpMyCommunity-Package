import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

import { AlertType } from '../../const/alert-type';
import { GlobalConstant } from '../../const/global-constant';

@Injectable({
  providedIn: 'root',
})
export class GlobalInfoService {
  private loaderSubject = new BehaviorSubject<boolean>(false);

  public constructor(private snackBar: MatSnackBar) {}

  public getLoaderObservable(): Observable<boolean> {
    return this.loaderSubject.asObservable();
  }

  public notifLoader(displayLoader: boolean): void {
    this.loaderSubject.next(displayLoader);
  }

  public showAlert(alertType: AlertType, message: string, duration?: number): void {
    this.snackBar.open(message, $localize`:@@global.close:Close`, {
      duration: duration ? duration : GlobalConstant.Display.NOTIFICATION_DELAY,
      panelClass: [this.getAlertClass(alertType)],
    } as MatSnackBarConfig);
  }

  private getAlertClass(alertType: AlertType): string {
    switch (alertType) {
      case AlertType.SUCCESS:
        return 'alertSuccess';
      case AlertType.INFO:
        return 'alertInfo';
      case AlertType.WARNING:
        return 'alertWarning';
      case AlertType.DANGER:
        return 'alertDanger';
      default:
        return 'alertDefault';
    }
  }
}
