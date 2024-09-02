import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { PostitService } from '../../service/postit/postit.service';
import { PostitNote } from '../../model/postit/postit-note';
import { Board } from '../../model/postit/board';
import { GlobalService } from '../../service/global/global.service';
import { GlobalConstant } from '../../const/global-constant';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { ViewListComponent } from './view-list/view-list.component';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { AlertType } from 'src/app/const/alert-type';
import { GlobalInfoService } from 'src/app/service/util/global-info.service';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
})
export class BoardComponent implements OnInit {
  public boardList: Array<Board> = [];
  public noteListMap = new Map<number, Array<PostitNote>>();

  public view = 'tabs';
  public selectedIndex = 0;

  public parameterNoteMax = 0;

  public constructor(
    private route: ActivatedRoute,
    private router: Router,
    private globalService: GlobalService,
    private postitService: PostitService,
    private globalInfoService: GlobalInfoService,
    private matBottomSheet: MatBottomSheet
  ) {}

  public ngOnInit(): void {
    this.globalService.getParameterValue(GlobalConstant.Parameter.NOTE_MAX).subscribe((paramValue) => {
      this.parameterNoteMax = Number(paramValue);
    });

    this.postitService.getBoardList().subscribe((boardList) => {
      this.boardList = boardList;
      this.route.params.subscribe((params) => {
        this.view = params['view'];
        if (!this.boardList || this.boardList.length === 0) {
          return;
        }
        if (this.view === 'table') {
          // In view "table", we load all boards
          this.boardList.forEach((board) => {
            if (board.id) {
              this.loadNoteList(board.id);
            }
          });
        } else if (this.view !== 'panels') {
          // In view "tabs" (default view), we load the selected tab
          if (params['id']) {
            const selectedBoardId = parseInt(params['id'], 10);
            this.loadNoteList(selectedBoardId);
            this.selectedIndex = this.boardList.findIndex((board) => board.id === selectedBoardId);
          } else {
            this.router.navigate(['board', { id: '' + this.boardList[0].id }]);
          }
        }
      });
    });
  }

  public changeTabBoard(event: MatTabChangeEvent): void {
    if (event.index < this.boardList.length) {
      const currentBoard = this.boardList[event.index];
      this.router.navigate(['board', { id: '' + currentBoard.id }]);
    }
  }

  public loadNoteList(boardId: number | undefined): void {
    if (!boardId) {
      return;
    }
    this.postitService.getNoteList(boardId).subscribe((noteList) => {
      this.noteListMap.set(boardId, noteList);
    });
  }

  public getNoteList(boardId: number): Array<PostitNote> {
    const noteList = this.noteListMap.get(boardId);
    if (!noteList) {
      return [];
    }
    return noteList;
  }

  public getOtherBoardList(boardId: number): Array<Board> {
    return this.boardList.filter((board) => board.id !== boardId);
  }

  public openChangeView(): void {
    this.matBottomSheet.open(ViewListComponent, {
      data: { currentView: this.view },
    });
  }

  public dropNote(event: CdkDragDrop<Board>): void {
    const originBoard = event.previousContainer.data;
    const destinationBoard = event.container.data;
    if (!originBoard.id || !destinationBoard.id) {
      return;
    }

    const draggedNote = event.item.data as PostitNote;
    const note = new PostitNote();
    note.id = draggedNote.id;
    note.boardId = destinationBoard.id;
    note.orderNum = event.currentIndex + 1;

    // Move the note before the update for avoid clipping with drag and drop
    const noteArray = this.noteListMap.get(destinationBoard.id);
    if (event.previousContainer === event.container) {
      if (noteArray) {
        moveItemInArray(noteArray, event.previousIndex, event.currentIndex);
      }
    } else {
      const previousNoteArray = this.noteListMap.get(originBoard.id);
      if (noteArray && previousNoteArray) {
        transferArrayItem(previousNoteArray, noteArray, event.previousIndex, event.currentIndex);
      }
    }

    this.postitService.updateNote(note).subscribe((updatedNote) => {
      this.globalInfoService.showAlert(
        AlertType.SUCCESS,
        $localize`:@@board.noteMovedWithDragAndDrop:Note moved with drag-and-drop`
      );
      if (!originBoard.id || !destinationBoard.id) {
        return;
      }

      this.loadNoteList(destinationBoard.id);
      if (originBoard.id !== destinationBoard.id) {
        this.loadNoteList(originBoard.id);
      }
    });
  }
}
