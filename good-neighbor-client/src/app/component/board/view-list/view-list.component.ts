import { Component, Inject } from '@angular/core';
import { MatBottomSheetRef, MAT_BOTTOM_SHEET_DATA } from '@angular/material/bottom-sheet';

@Component({
  selector: 'app-view-list',
  templateUrl: './view-list.component.html',
  styleUrls: ['./view-list.component.scss'],
})
export class ViewListComponent {
  public currentView = 'tabs';

  constructor(
    private matbottomSheetRef: MatBottomSheetRef<ViewListComponent>,
    @Inject(MAT_BOTTOM_SHEET_DATA) public data: any
  ) {
    this.currentView = data.currentView;
  }

  public openLink(event: MouseEvent): void {
    this.matbottomSheetRef.dismiss();
  }

  public getDynamicClass(linkView: string): string {
    if (linkView === this.currentView || (!this.currentView && linkView === 'tabs')) {
      return 'viewSelected';
    } else {
      return '';
    }
  }
}
