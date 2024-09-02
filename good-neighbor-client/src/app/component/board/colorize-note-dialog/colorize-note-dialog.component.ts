import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { PostitService } from 'src/app/service/postit/postit.service';
import { PostitNote } from 'src/app/model/postit/postit-note';

export interface ColorizeNoteDialogData {
  noteId: number;
}

@Component({
  selector: 'app-colorize-note-dialog',
  templateUrl: './colorize-note-dialog.component.html',
  styleUrls: ['./colorize-note-dialog.component.scss'],
})
export class ColorizeNoteDialogComponent {
  public constructor(
    private dialogRef: MatDialogRef<ColorizeNoteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ColorizeNoteDialogData,
    private postitService: PostitService
  ) {}

  public chooseColor(color: string): void {
    const saveNote = new PostitNote();
    saveNote.id = this.data.noteId;
    saveNote.color = color;

    this.postitService.updateNote(saveNote).subscribe((updatedNote) => {
      this.dialogRef.close(updatedNote);
    });
  }
}
