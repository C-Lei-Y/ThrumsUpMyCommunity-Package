import { Component, OnInit } from '@angular/core';
import { appInfo } from '../../app.info';
import { HttpClient } from '@angular/common/http';
import { UrlConstant } from 'src/app/const/url-constant';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.scss'],
})
export class InfoComponent implements OnInit {
  public clientInfo = appInfo;
  public serverInfo: any;

  public constructor(private httpClient: HttpClient) {}

  public ngOnInit(): void {
    this.httpClient.get(UrlConstant.ACTUATOR_INFO).subscribe((info) => (this.serverInfo = info));
  }
}
