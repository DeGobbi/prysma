import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroUsuarioComponent } from './components/usuario/cadastro-usuario/cadastro-usuario.component';
import { LoginComponent } from './components/usuario/login/login.component';
import { ProdutosComponent } from './components/produtos/produtos.component';
import { AuthGuard } from './guard/auth.guard';

export const routes: Routes = [
    { path: 'cadastrar', component: CadastroUsuarioComponent },
    { path: 'login', component: LoginComponent },
    { path: 'produtos', component: ProdutosComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }