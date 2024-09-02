import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatDialog } from '@angular/material/dialog';
import { BoardNoteComponent } from './board-note.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AppMaterialModule } from 'src/app/app-material.module';

describe('BoardNoteComponent', () => {
  let component: BoardNoteComponent;
  let fixture: ComponentFixture<BoardNoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AppMaterialModule],
      declarations: [BoardNoteComponent],
      providers: [MatDialog],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoardNoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
