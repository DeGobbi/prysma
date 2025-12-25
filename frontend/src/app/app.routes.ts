import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroUsuarioComponent } from './components/usuario/cadastro-usuario/cadastro-usuario.component';
import { LoginComponent } from './components/usuario/login/login.component';
import { CadastroProdutoComponent } from './components/produto/cadastro-produto/cadastro-produto.component';
import { AuthGuard } from './guards/auth/auth.guard';
import { AdminGuard } from './guards/admin/admin.guard';
import { HomeComponent } from './components/home/home.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'cadastrar', component: CadastroUsuarioComponent },
    { path: 'login', component: LoginComponent },
    { path: 'produtos', component: CadastroProdutoComponent, canActivate: [AuthGuard, AdminGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }