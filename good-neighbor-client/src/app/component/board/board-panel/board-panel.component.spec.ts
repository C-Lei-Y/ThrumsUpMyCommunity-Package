import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BoardPanelComponent } from './board-panel.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AppMaterialModule } from 'src/app/app-material.module';

describe('BoardPanelComponent', () => {
  let component: BoardPanelComponent;
  let fixture: ComponentFixture<BoardPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AppMaterialModule],
      declarations: [BoardPanelComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoardPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
