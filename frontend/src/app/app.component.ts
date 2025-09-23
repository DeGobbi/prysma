import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AppRoutingModule } from './app.routes';
import { CadastroUsuarioComponent } from './components/usuario/cadastro-usuario/cadastro-usuario.component';
import { HeaderComponent } from './components/header/header.component';
import { UsuarioService } from './services/usuario/usuario.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CadastroUsuarioComponent, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {
  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.usuarioService.carregarUsuario().subscribe();
  }
}
