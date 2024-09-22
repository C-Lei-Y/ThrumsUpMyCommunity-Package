import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Client, StompConfig } from '@stomp/stompjs';
import { UrlConstant } from '../../const/url-constant';
import { CountLikes } from '../../model/global/count-likes';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class LikeService {
  private static readonly COUNT_LIKE_TIMER = environment.likeTimerSecond;
  private static readonly LIKE_WEB_SOCKET = environment.likeWebSocket;

  private countLikeSubject = new Subject<number>();

  private stompClient: Client | undefined;

  public constructor(private httpClient: HttpClient) {}

  public getCountLikeObservable(): Observable<number> {
    return this.countLikeSubject.asObservable();
  }

  public listenCountLikeTimer(): void {
    this.countLike();

    if (LikeService.LIKE_WEB_SOCKET) {
      // Use WebSocket (with Stomp)
      const config = new StompConfig();
      config.brokerURL = this.convertToWebSocketUrl(UrlConstant.WebSocket.CONNECTION);
      config.onConnect = (receipt) => {
        this.countLike();

        if (!this.stompClient) {
          return;
        }

        this.stompClient.subscribe(UrlConstant.WebSocket.LISTEN_LIKE_COUNT, (countLikesMsg) => {
          if (countLikesMsg.command === 'MESSAGE') {
            const countLikes = JSON.parse(countLikesMsg.body) as CountLikes;
            this.countLikeSubject.next(countLikes.count);
          } else {
            console.error('Wrong message from the websocket', countLikesMsg);
          }
        });
      };
      this.stompClient = new Client(config);
      this.stompClient.activate();
    } else {
      // Use HTTP GET periodically
      if (LikeService.COUNT_LIKE_TIMER > 0) {
        setInterval(() => {
          this.countLike();
        }, LikeService.COUNT_LIKE_TIMER * 1000);
      }
    }
  }

  public addLike(): void {
    this.httpClient.post<void>(UrlConstant.Global.LIKE, null).subscribe(() => {
      if (!LikeService.LIKE_WEB_SOCKET) {
        this.countLike();
      }
    });
  }

  private countLike(): void {
    this.httpClient.get<CountLikes>(UrlConstant.Global.LIKE_COUNT).subscribe((countLikes) => {
      this.countLikeSubject.next(countLikes.count);
    });
  }

  private convertToWebSocketUrl(path: string): string {
    let webSocketUrl = 'ws:';
    if (window.location.protocol === 'https:') {
      webSocketUrl = 'wss:';
    }
    webSocketUrl += '//' + window.location.host;
    webSocketUrl += path;
    return webSocketUrl;
  }
}
