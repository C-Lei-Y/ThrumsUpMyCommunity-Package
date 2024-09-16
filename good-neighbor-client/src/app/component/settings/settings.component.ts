import { Component, OnInit, ViewChild } from '@angular/core';

import { AuthService } from '../../service/auth/auth.service';
import { BoardSettingsComponent } from './board-settings/board-settings.component';
import { UserSettingsComponent } from './user-settings/user-settings.component';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent implements OnInit {
  @ViewChild('boardSettings')
  public boardSettings: BoardSettingsComponent | undefined;

  @ViewChild('userSettings')
  public userSettings: UserSettingsComponent | undefined;

  public constructor(private authService: AuthService) {}

  public ngOnInit(): void {
    this.authService.getRefreshedCurrentUser();
  }

  public refresh(): void {
    if (this.boardSettings) {
      this.boardSettings.refresh();
    }
    if (this.userSettings) {
      this.userSettings.refresh();
    }
  }
}
