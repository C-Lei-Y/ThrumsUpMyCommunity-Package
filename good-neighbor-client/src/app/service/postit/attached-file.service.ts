import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UrlConstant } from 'src/app/const/url-constant';
import { AttachedFile } from 'src/app/model/postit/attached-file';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AttachedFileService {
  constructor(private httpClient: HttpClient) {}

  public uploadFile(file: File, noteId: number): Observable<AttachedFile> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.httpClient.put<AttachedFile>(`${UrlConstant.Postit.NOTES}/${noteId}/attached-file`, formData);
  }

  public getDownloadLink(attachedFileId: number): string {
    return `${UrlConstant.Postit.ATTACHED_FILES}/${attachedFileId}/content`;
  }

  public downloadFile(attachedFileId: number): Observable<Blob> {
    return this.httpClient.get(this.getDownloadLink(attachedFileId), { responseType: 'blob' });
  }

  public deleteFile(attachedFileId: number): Observable<void> {
    return this.httpClient.delete<void>(UrlConstant.Postit.ATTACHED_FILES + '/' + attachedFileId);
  }
}
