import { TestBed } from '@angular/core/testing';
import { PostitService } from './postit.service';
import { Board } from '../../model/postit/board';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UrlConstant } from 'src/app/const/url-constant';

let postitService: PostitService;
let httpMock: HttpTestingController;

describe('PostitService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });

    postitService = TestBed.inject(PostitService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('getBoardList should call the correct url', () => {
    const boardList: Array<Board> = [];
    boardList.push({ id: 1, name: 'Test 1' });
    boardList.push({ id: 2, name: 'Test 2' });

    postitService.getBoardList().subscribe((result) => {
      expect(result).toBe(boardList);
    });

    const mockRequest = httpMock.expectOne(UrlConstant.Postit.BOARDS);
    expect(mockRequest.request.method).toEqual('GET');
    expect(mockRequest.request.responseType).toEqual('json');
    mockRequest.flush(boardList);
    httpMock.verify();
  });
});
