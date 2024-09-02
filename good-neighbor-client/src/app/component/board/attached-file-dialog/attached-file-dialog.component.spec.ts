import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttachedFileDialogComponent } from './attached-file-dialog.component';
import { AppMaterialModule } from 'src/app/app-material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

describe('AttachedFileDialogComponent', () => {
  let component: AttachedFileDialogComponent;
  let fixture: ComponentFixture<AttachedFileDialogComponent>;

  beforeEach(async () => {
    const mockDialogRef = {
      close: jasmine.createSpy('close'),
    };

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AppMaterialModule],
      declarations: [AttachedFileDialogComponent],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: mockDialogRef },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttachedFileDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
