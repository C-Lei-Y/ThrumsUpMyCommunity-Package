import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ColorizeNoteDialogComponent } from './colorize-note-dialog.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AppMaterialModule } from 'src/app/app-material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ColorizeNoteDialogComponent', () => {
  let component: ColorizeNoteDialogComponent;
  let fixture: ComponentFixture<ColorizeNoteDialogComponent>;

  beforeEach(async () => {
    const mockDialogRef = {
      close: jasmine.createSpy('close'),
    };

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AppMaterialModule],
      declarations: [ColorizeNoteDialogComponent],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: mockDialogRef },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ColorizeNoteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
