import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterUserComponent } from './pages/register-user/register-user.component';
import { LoginComponent } from './pages/login/login.component';
import { IndexComponent } from './pages/index/index.component';
import { SangreTigoLogoComponent } from './components/sangre-tigo-logo/sangre-tigo-logo.component';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UploadPhotoComponent } from './pages/upload-photo/upload-photo.component';
import { FeedNewsComponent } from './pages/feed-news/feed-news.component';
import { NewComponent } from './components/new/new.component';
import { PublisherComponent } from './components/publisher/publisher.component';
import { HeaderComponent } from './components/header/header.component';
import { PipesModule } from './pipes/pipes.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Imports angular Material
import { TextFieldModule } from '@angular/cdk/text-field';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { InteresesComponent } from './pages/intereses/intereses.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { NoticiaComponent } from './pages/noticia/noticia.component';
import { ModalReaccionesComponent } from './components/modal-reacciones/modal-reacciones.component';
import { MatDialogModule} from '@angular/material/dialog';
import { MatTabsModule} from '@angular/material/tabs';
import { SearchPeopleComponent } from './pages/search-people/search-people.component';
import { ModalReportarComponent } from './components/modal-reportar/modal-reportar.component';
import { MatRadioModule} from '@angular/material/radio';
import { ReportesComponent } from './pages/reportes/reportes.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { NewsComponent } from './components/news/news.component';
import { ModalInfoPageComponent } from './components/modal-info-page/modal-info-page.component';
import { TagComponent } from './components/tag/tag.component';
import { TagInputModule } from 'ngx-chips';


@NgModule({
  entryComponents: [
    ModalReaccionesComponent,
    ModalReportarComponent
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    IndexComponent,
    RegisterUserComponent,
    SangreTigoLogoComponent,
    UploadPhotoComponent,
    FeedNewsComponent,
    NewComponent,
    PublisherComponent,
    HeaderComponent,
    PerfilComponent,
    InteresesComponent,
    NoticiaComponent,
    ModalReaccionesComponent,
    SearchPeopleComponent,
    ModalReportarComponent,
    ReportesComponent,
    NewsComponent,
    ModalInfoPageComponent,
    TagComponent,
  ],
  imports: [
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatSnackBarModule,
    TextFieldModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    PipesModule,
    BrowserAnimationsModule,
    ScrollingModule,
    MatDialogModule,
    MatTabsModule,
    MatRadioModule,
    InfiniteScrollModule,
    ReactiveFormsModule,
    TagInputModule, 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
