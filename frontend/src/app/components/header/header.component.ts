import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario/usuario.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  userDropdownAtivo = false;
  dropdownAtivo = false;
  isLoginOpen = false;
  searchQuery: string = '';

  constructor(
    public usuarioService: UsuarioService,
    private router: Router
  ) {}

  logout() {
    this.usuarioService.logout().subscribe({
      next: () => {
        window.location.reload();
        this.router.navigate(['/']);
      },
      error: (err) => console.error('Erro ao fazer logout:', err)
    });
  }

  buscar() {
    if (this.searchQuery.trim().length === 0) return;

    this.router.navigate(['/buscar'], {
      queryParams: { q: this.searchQuery }
    });
  }
toggleLoginDropdown(event?: MouseEvent) {
    event?.stopPropagation(); // evita que document:click feche imediatamente
    this.isLoginOpen = !this.isLoginOpen;
  }

  closeLoginDropdown() {
    this.isLoginOpen = false;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    // fecha se clicar fora
    this.isLoginOpen = false;
  }
  
}