import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from '../../service/auth/auth.service';
import { User } from '../../model/global/user';
import { GlobalInfoService } from '../../service/util/global-info.service';
import { AlertType } from '../../const/alert-type';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  public userConnected: User | null = null;

  public loginForm: FormGroup;
  public loginInProgress = false;

  public constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private globalInfoService: GlobalInfoService
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  public ngOnInit(): void {
    this.authService.getCurrentUser().subscribe((user) => {
      this.userConnected = user;
    });
    this.authService.getRefreshedCurrentUser();
  }

  public onSubmit(): void {
    if (!this.loginForm || this.loginForm.invalid) {
      return;
    }

    this.loginInProgress = true;
    const valueForm = this.loginForm.value;

    this.authService.login(valueForm.username, valueForm.password).subscribe((isSignIn) => {
      this.loginInProgress = false;
      if (!isSignIn) {
        this.globalInfoService.showAlert(
          AlertType.WARNING,
          $localize`:@@login.signinFailed:Sign-in Failed : Incorrect username or password`,
          3000
        );
      }
    });
  }

  public logout(): void {
    this.authService.logout();
  }
}
