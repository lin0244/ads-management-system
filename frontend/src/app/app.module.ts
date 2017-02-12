import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Http, HttpModule, RequestOptions, XHRBackend } from '@angular/http';

import { AppComponent } from './app.component';
import { AmsHttp, UserService } from './service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [
    {
      provide: Http,
      useFactory: (backend: XHRBackend, options: RequestOptions) => {
        return new AmsHttp(backend, options);
      },
      deps: [XHRBackend, RequestOptions]
    },
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
