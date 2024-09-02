import { Component, Inject } from '@angular/core';
import { AttachedFile } from 'src/app/model/postit/attached-file';
import { AttachedFileService } from 'src/app/service/postit/attached-file.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AlertType } from 'src/app/const/alert-type';
import { GlobalInfoService } from 'src/app/service/util/global-info.service';

@Component({
  selector: 'app-attached-file-dialog',
  templateUrl: './attached-file-dialog.component.html',
  styleUrls: ['./attached-file-dialog.component.scss'],
})
export class AttachedFileDialogComponent {
  public constructor(
    private dialogRef: MatDialogRef<AttachedFileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public attachedFile: AttachedFile,
    private attachedFileService: AttachedFileService,
    private globalInfoService: GlobalInfoService
  ) {}

  public download(): void {
    if (!this.attachedFile.id) {
      return;
    }

    // Solution that always works even with add blocker
    window.open(this.attachedFileService.getDownloadLink(this.attachedFile.id), '_blank');
    this.dialogRef.close(false);
  }

  public deleteFile(): void {
    if (!this.attachedFile?.id) {
      return;
    }

    this.attachedFileService.deleteFile(this.attachedFile.id).subscribe(() => {
      this.globalInfoService.showAlert(
        AlertType.SUCCESS,
        $localize`:@@attachedFile.attachmentDeleted:Attachment deleted`
      );
      this.dialogRef.close(true);
    });
  }
}
