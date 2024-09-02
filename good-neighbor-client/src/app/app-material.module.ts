import { NgModule } from '@angular/core';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatBadgeModule } from '@angular/material/badge';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatMenuModule } from '@angular/material/menu';
import { MatListModule } from '@angular/material/list';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { DragDropModule } from '@angular/cdk/drag-drop';

@NgModule({
  exports: [
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatBadgeModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    MatCardModule,
    MatSnackBarModule,
    MatDialogModule,
    MatInputModule,
    MatExpansionModule,
    MatTableModule,
    MatPaginatorModule,
    MatSelectModule,
    MatCheckboxModule,
    MatProgressBarModule,
    MatMenuModule,
    MatListModule,
    MatBottomSheetModule,
    DragDropModule
  ]
})
export class AppMaterialModule {}
