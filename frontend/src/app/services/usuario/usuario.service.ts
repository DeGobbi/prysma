import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface Usuario {
  id?: number;
  nome: string;
  email: string;
  senha?: string;
  roles?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8080/api/usuarios';
  private usuarioSubject = new BehaviorSubject<Usuario | null>(null);
  usuario$ = this.usuarioSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  // ---------- CRUD ----------
  criarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario);
  }

  // ---------- LOGIN / LOGOUT ----------
  login(email: string, senha: string): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/login`, { email, senha }, {withCredentials: true}).pipe(
      tap(user => {
        this.usuarioSubject.next(user);
      })
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/logout`, {}, { withCredentials: true })
      .pipe(
        tap(() => {
          this.usuarioSubject.next(null)
          this.router.navigate(['/']);
        })
      );
  }

  // ---------- SESSÃO / AUTENTICAÇÃO ----------
  carregarUsuario(): Observable<Usuario | null> {
  return this.http.get<Usuario>(`${this.apiUrl}/me`, { withCredentials: true }).pipe(
    tap(usuario => this.usuarioSubject.next(usuario)),
    catchError(() => {
      this.usuarioSubject.next(null);
      return of(null);
    })
  );
}

  get usuarioAtual(): Usuario | null {
    return this.usuarioSubject.value;
  }

  isLoggedIn(): boolean {
    return this.usuarioSubject !== null;
  }
}