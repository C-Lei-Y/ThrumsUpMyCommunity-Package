import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditNoteDialogComponent } from './edit-note-dialog.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AppMaterialModule } from 'src/app/app-material.module';

describe('EditNoteDialogComponent', () => {
  let component: EditNoteDialogComponent;
  let fixture: ComponentFixture<EditNoteDialogComponent>;

  beforeEach(async () => {
    const mockDialogRef = {
      close: jasmine.createSpy('close'),
    };

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AppMaterialModule],
      declarations: [EditNoteDialogComponent],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: mockDialogRef },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditNoteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
