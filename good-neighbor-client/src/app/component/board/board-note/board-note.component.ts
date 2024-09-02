import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { PostitNote } from '../../../model/postit/postit-note';
import { ConfirmDialogComponent, ConfirmDialogData } from '../../shared/dialog/confirm-dialog/confirm-dialog.component';
import { PostitService } from '../../../service/postit/postit.service';
import { GlobalInfoService } from '../../../service/util/global-info.service';
import { AlertType } from '../../../const/alert-type';
import { EditNoteDialogComponent } from '../edit-note-dialog/edit-note-dialog.component';
import { GlobalConstant } from '../../../const/global-constant';
import { ColorizeNoteDialogComponent } from '../colorize-note-dialog/colorize-note-dialog.component';
import { Board } from 'src/app/model/postit/board';
import { AttachedFileService } from 'src/app/service/postit/attached-file.service';
import { AttachedFileDialogComponent } from '../attached-file-dialog/attached-file-dialog.component';
import { GlobalService } from 'src/app/service/global/global.service';

@Component({
  selector: 'app-board-note',
  templateUrl: './board-note.component.html',
  styleUrls: ['./board-note.component.scss'],
})
export class BoardNoteComponent implements OnInit {
  @Input()
  public note: PostitNote;
  @Input()
  public otherBoardList: Array<Board> = [];

  @Output()
  public takeOffNote = new EventEmitter<PostitNote>();
  @Output()
  public changeNote = new EventEmitter<PostitNote>();
  @Output()
  public orderNote = new EventEmitter<PostitNote>();
  @Output()
  public moveNote = new EventEmitter<PostitNote>();

  private parameterUploadSizeMax = 0;

  public constructor(
    private dialog: MatDialog,
    private globalInfoService: GlobalInfoService,
    private globalService: GlobalService,
    private postitService: PostitService,
    private attachedFileService: AttachedFileService
  ) {
    this.note = new PostitNote();
  }

  public ngOnInit(): void {
    this.globalService.getParameterValue(GlobalConstant.Parameter.UPLOAD_SIZE_MAX).subscribe((parameterValue) => {
      this.parameterUploadSizeMax = Number(parameterValue);
    });
  }

  public getColorClass(): string {
    if (this.note.color && GlobalConstant.Functional.VALID_COLOR_LIST.includes(this.note.color)) {
      return this.note.color;
    } else {
      return GlobalConstant.Functional.DEFAULT_COLOR;
    }
  }

  public changeOrder(orderIncrement: number): void {
    const saveNote = new PostitNote();
    saveNote.id = this.note.id;
    saveNote.orderNum = (this.note.orderNum ? this.note.orderNum : 0) + orderIncrement;

    this.postitService.updateNote(saveNote).subscribe((updatedNote) => {
      this.note = updatedNote;
      this.orderNote.emit(this.note);
    });
  }

  public changeToBoard(board: Board): void {
    const moveNote = new PostitNote();
    moveNote.id = this.note.id;
    moveNote.boardId = board.id;

    this.postitService.updateNote(moveNote).subscribe((updatedNote) => {
      this.globalInfoService.showAlert(AlertType.SUCCESS, $localize`:@@boardNote.noteMoved:Note moved`);
      this.moveNote.emit(updatedNote);
    });
  }

  public edit(): void {
    if (!this.note.id) {
      return;
    }

    this.postitService.getNote(this.note.id).subscribe((editedNote) => {
      const editDialog = this.dialog.open(EditNoteDialogComponent, {
        width: GlobalConstant.Display.DIALOG_WIDTH,
        data: {
          editedNote,
        },
      });

      editDialog.afterClosed().subscribe((updatedNote) => {
        this.changeNoteAfterUpdate(updatedNote, $localize`:@@boardNote.noteUpdated:Note updated`);
      });
    });
  }

  public changeColor(): void {
    const colorDialog = this.dialog.open(ColorizeNoteDialogComponent, {
      width: GlobalConstant.Display.DIALOG_WIDTH,
      data: {
        noteId: this.note.id,
      },
    });

    colorDialog.afterClosed().subscribe((updatedNote) => {
      this.changeNoteAfterUpdate(updatedNote);
    });
  }

  public viewAttachedFile(): void {
    const attachedFileDialog = this.dialog.open(AttachedFileDialogComponent, {
      width: GlobalConstant.Display.DIALOG_WIDTH,
      data: this.note.attachedFile,
    });

    attachedFileDialog.afterClosed().subscribe((deleteAttachedFile) => {
      if (deleteAttachedFile) {
        this.note.attachedFile = null;
        this.changeNote.emit(this.note);
      }
    });
  }

  public uploadFile(eventUpload: EventTarget | undefined | null): void {
    const uploadFile = (eventUpload as any)?.files as FileList | null;
    if (!uploadFile || uploadFile.length !== 1) {
      this.globalInfoService.showAlert(
        AlertType.WARNING,
        $localize`:@@boardNote.fileNotSelected:Please select one file`
      );
      return;
    }
    if (uploadFile[0].size > this.parameterUploadSizeMax) {
      this.globalInfoService.showAlert(AlertType.WARNING, $localize`:@@boardNote.fileTooLarge:The file is too large`);
      return;
    }

    if (uploadFile && uploadFile.length > 0 && this.note.id) {
      this.attachedFileService.uploadFile(uploadFile[0], this.note.id).subscribe((attachedFile) => {
        this.note.attachedFile = attachedFile;
        this.changeNoteAfterUpdate(this.note, $localize`:@@boardNote.fileUploaded:File uploaded`);
      });
    }
  }

  public deleteNote(): void {
    const confirmDialogData = {
      title: $localize`:@@boardNote.deleteNote:Delete note`,
      message: $localize`:@@boardNote.deleteNoteConfirm:Are you sure to delete this note ?`,
      confirm: $localize`:@@global.delete:Delete`,
    } as ConfirmDialogData;

    const confirmDialog = this.dialog.open(ConfirmDialogComponent, {
      width: GlobalConstant.Display.DIALOG_WIDTH,
      data: confirmDialogData,
    });

    confirmDialog.afterClosed().subscribe((confirmation) => {
      if (confirmation === true && this.note.id) {
        this.postitService.deleteNote(this.note.id).subscribe(() => {
          this.globalInfoService.showAlert(AlertType.SUCCESS, $localize`:@@boardNote.noteDeleted:Note deleted`);
          this.takeOffNote.emit(this.note);
        });
      }
    });
  }

  private changeNoteAfterUpdate(updatedNote: PostitNote, message?: string): void {
    if (updatedNote) {
      Object.assign(this.note, updatedNote);
      this.changeNote.emit(this.note);
      if (message) {
        this.globalInfoService.showAlert(AlertType.SUCCESS, message);
      }
    }
  }
}
