import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { PostitNote } from '../../../model/postit/postit-note';
import { PostitService } from 'src/app/service/postit/postit.service';

export interface EditNoteDialogData {
  editedNote: PostitNote;
}

@Component({
  selector: 'app-edit-note-dialog',
  templateUrl: './edit-note-dialog.component.html',
  styleUrls: ['./edit-note-dialog.component.scss'],
})
export class EditNoteDialogComponent {
  public editedNote: PostitNote;

  public constructor(
    private dialogRef: MatDialogRef<EditNoteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EditNoteDialogData,
    private postitService: PostitService
  ) {
    this.editedNote = data.editedNote;
  }

  public saveEdit(): void {
    const saveNote = new PostitNote();
    saveNote.id = this.editedNote.id;
    saveNote.name = this.editedNote.name;
    saveNote.text = this.editedNote.text;

    this.postitService.updateNote(saveNote).subscribe((updatedNote) => {
      this.dialogRef.close(updatedNote);
    });
  }
}
