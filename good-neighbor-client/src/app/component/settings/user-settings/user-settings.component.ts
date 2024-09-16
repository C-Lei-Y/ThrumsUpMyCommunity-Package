import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

import { User } from 'src/app/model/global/user';
import { MatDialog } from '@angular/material/dialog';
import {
  ConfirmDialogData,
  ConfirmDialogComponent,
} from 'src/app/component/shared/dialog/confirm-dialog/confirm-dialog.component';
import { GlobalConstant } from 'src/app/const/global-constant';
import { UserService } from 'src/app/service/global/user.service';
import { AlertType } from 'src/app/const/alert-type';
import { GlobalInfoService } from 'src/app/service/util/global-info.service';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.scss'],
})
export class UserSettingsComponent implements OnInit {
  @ViewChild(MatPaginator, { static: true })
  public paginator: MatPaginator | undefined;

  public readonly passwordPattern = '^\\w{5,}$';
  public displayedColumns = ['id', 'username', 'password', 'roleList', 'enabled', 'actions'];
  public dataSource = new MatTableDataSource<User>();

  public roleList: Array<string> = [];

  public constructor(
    private dialog: MatDialog,
    private userService: UserService,
    private globalInfoService: GlobalInfoService
  ) {}

  public ngOnInit(): void {
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
    }
    this.getUserList();
    this.getRoleList();
  }

  public refresh(): void {
    this.getUserList();
  }

  public isValidForSave(user: User): boolean {
    return !!(
      user &&
      user.username &&
      user.username.length > 1 &&
      (!user.password || new RegExp(this.passwordPattern).test(user.password))
    );
  }

  public saveUser(user: User): void {
    if (!this.isValidForSave(user)) {
      return;
    }
    this.userService.updateUser(user).subscribe((updatedUser) => {
      this.globalInfoService.showAlert(AlertType.SUCCESS, $localize`:@@userSettings.userUpdated:User updated`);
      this.getUserList();
    });
  }

  public deleteUser(user: User): void {
    const confirmDialogData = {
      title: $localize`:@@userSettings.deleteUser:Delete user`,
      message: $localize`:@@userSettings.deleteUserConfirm:Are you sure to delete this user ?`,
      confirm: $localize`:@@global.delete:Delete`,
    } as ConfirmDialogData;

    const confirmDialog = this.dialog.open(ConfirmDialogComponent, {
      width: GlobalConstant.Display.DIALOG_WIDTH,
      data: confirmDialogData,
    });

    confirmDialog.afterClosed().subscribe((confirmation) => {
      if (confirmation === true && user.id) {
        this.userService.deleteUser(user.id).subscribe(() => {
          this.globalInfoService.showAlert(AlertType.SUCCESS, $localize`:@@userSettings.userDeleted:User deleted`);
          this.getUserList();
        });
      }
    });
  }

  public createUser(): void {
    const newUser = new User();
    newUser.username = $localize`:@@userSettings.username:username`;
    newUser.enabled = false;

    this.userService.createUser(newUser).subscribe((createdUser) => {
      this.globalInfoService.showAlert(AlertType.SUCCESS, $localize`:@@userSettings.userCreated:User created`);
      this.getUserList();
    });
  }

  private getUserList(): void {
    this.userService.getUserList().subscribe((userList) => {
      this.dataSource.data = userList;
    });
  }

  private getRoleList(): void {
    this.userService.getRoleList().subscribe((roleList) => {
      this.roleList = roleList;
    });
  }
}
