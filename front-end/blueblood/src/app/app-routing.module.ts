import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterUserComponent } from './pages/register-user/register-user.component';
import { IndexComponent } from './pages/index/index.component';
import { LoginComponent } from './pages/login/login.component';
import { UploadPhotoComponent } from './pages/upload-photo/upload-photo.component';
import { FeedNewsComponent } from './pages/feed-news/feed-news.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { InteresesComponent } from './pages/intereses/intereses.component';
import { NoticiaComponent } from './pages/noticia/noticia.component';
import { SearchPeopleComponent } from './pages/search-people/search-people.component';
import { ReportesComponent } from './pages/reportes/reportes.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {path: '', component: IndexComponent},
  {path: 'registrar', component: RegisterUserComponent},
  {path: 'login', component: LoginComponent},
  {path: 'fotoPerfil', component: UploadPhotoComponent, canActivate: [AuthGuard]},
  {path: 'fotoPerfil/:edit', component: UploadPhotoComponent, canActivate: [AuthGuard]},
  {path: 'noticias', component: FeedNewsComponent, canActivate: [AuthGuard]},
  {path: 'noticia/:id', component: NoticiaComponent, canActivate: [AuthGuard]},
  {path: 'perfil', component: PerfilComponent, canActivate: [AuthGuard]},
  {path: 'perfil/:id', component: PerfilComponent, canActivate: [AuthGuard]},
  {path: 'intereses', component: InteresesComponent, canActivate: [AuthGuard]},
  {path: 'buscar/:name', component: SearchPeopleComponent, canActivate: [AuthGuard]},
  {path: 'reportes', component: ReportesComponent, canActivate: [AuthGuard]},
  {path: '**', component: IndexComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    scrollPositionRestoration: 'enabled',
    anchorScrolling: 'enabled',
    useHash: true
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
