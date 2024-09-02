import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { BoardComponent } from './board.component';
import { of } from 'rxjs';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppMaterialModule } from 'src/app/app-material.module';
import { UrlConstant } from 'src/app/const/url-constant';
import { Board } from 'src/app/model/postit/board';
import { GlobalConstant } from 'src/app/const/global-constant';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BoardPanelComponent } from './board-panel/board-panel.component';
import { BoardNoteComponent } from './board-note/board-note.component';
import { PostitNote } from 'src/app/model/postit/postit-note';

describe('BoardComponent', () => {
  let component: BoardComponent;
  let fixture: ComponentFixture<BoardComponent>;
  let httpMock: HttpTestingController;

  const mockStandardRequest = () => {
    const mockRequestParamMax = httpMock.expectOne(
      UrlConstant.Global.PARAMETERS + '/' + GlobalConstant.Parameter.NOTE_MAX
    );
    mockRequestParamMax.flush('10');

    const mockRequestBoards = httpMock.expectOne(UrlConstant.Postit.BOARDS);
    mockRequestBoards.flush([
      { id: 1, name: 'Board1' },
      { id: 2, name: 'Board2' },
      { id: 3, name: 'Board3' },
    ] as Array<Board>);
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([
          {
            path: 'board',
            component: BoardComponent,
          },
        ]),
        AppMaterialModule,
        BrowserAnimationsModule,
      ],
      declarations: [BoardComponent, BoardPanelComponent, BoardNoteComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ view: 'panel' }),
          },
        },
      ],
      schemas: [],
    }).compileComponents();
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should create', () => {
    fixture = TestBed.createComponent(BoardComponent);
    component = fixture.componentInstance;
    fixture.autoDetectChanges(true);
    expect(component).toBeTruthy();
  });

  it('should init in tabs view', async () => {
    const activatedRouteMock = TestBed.inject(ActivatedRoute);
    activatedRouteMock.params = of({ id: '2' });
    fixture = TestBed.createComponent(BoardComponent);
    component = fixture.componentInstance;
    fixture.autoDetectChanges(true);

    mockStandardRequest();

    await fixture.whenStable();
    expect(component.parameterNoteMax).toEqual(10);
    const noteRequestNote = httpMock.expectOne(UrlConstant.Postit.NOTES + '?boardId=2');
    noteRequestNote.flush([{ id: 1, name: 'name', text: 'text', boardId: 2 }] as Array<PostitNote>);
    httpMock.verify();

    await fixture.whenStable();
    expect(component.view).toBeFalsy();
    expect(component.selectedIndex).toEqual(1);
    expect(component.noteListMap.size).toEqual(1);
    expect(fixture.nativeElement.querySelector('.mat-tab-group')).toBeTruthy();
    expect(fixture.nativeElement.querySelector('.mat-accordion')).toBeFalsy();
    expect(fixture.nativeElement.querySelector('.flexTable')).toBeFalsy();
  });

  it('should init in panel view', async () => {
    const activatedRouteMock = TestBed.inject(ActivatedRoute);
    activatedRouteMock.params = of({ view: 'panels' });
    fixture = TestBed.createComponent(BoardComponent);
    component = fixture.componentInstance;
    fixture.autoDetectChanges(true);

    mockStandardRequest();

    await fixture.whenStable();
    httpMock.verify();
    expect(component.view).toEqual('panels');
    expect(component.noteListMap.size).toEqual(0);
    expect(fixture.nativeElement.querySelector('.mat-tab-group')).toBeFalsy();
    expect(fixture.nativeElement.querySelector('.mat-accordion')).toBeTruthy();
    expect(fixture.nativeElement.querySelector('.flexTable')).toBeFalsy();
  });

  it('should init in table view', async () => {
    const activatedRouteMock = TestBed.inject(ActivatedRoute);
    activatedRouteMock.params = of({ view: 'table' });
    fixture = TestBed.createComponent(BoardComponent);
    component = fixture.componentInstance;
    fixture.autoDetectChanges(true);

    mockStandardRequest();

    await fixture.whenStable();
    const noteRequestNote1 = httpMock.expectOne(UrlConstant.Postit.NOTES + '?boardId=1');
    noteRequestNote1.flush([{ id: 1, name: 'name', text: 'text', boardId: 1 }] as Array<PostitNote>);
    const noteRequestNote2 = httpMock.expectOne(UrlConstant.Postit.NOTES + '?boardId=2');
    noteRequestNote2.flush([{ id: 2, name: 'name', text: 'text', boardId: 2 }] as Array<PostitNote>);
    const noteRequestNote3 = httpMock.expectOne(UrlConstant.Postit.NOTES + '?boardId=3');
    noteRequestNote3.flush([{ id: 3, name: 'name', text: 'text', boardId: 3 }] as Array<PostitNote>);
    httpMock.verify();

    await fixture.whenStable();
    expect(component.view).toEqual('table');
    expect(component.noteListMap.size).toEqual(3);
    expect(fixture.nativeElement.querySelector('.mat-tab-group')).toBeFalsy();
    expect(fixture.nativeElement.querySelector('.mat-accordion')).toBeFalsy();
    expect(fixture.nativeElement.querySelector('.flexTable')).toBeTruthy();
  });
});
