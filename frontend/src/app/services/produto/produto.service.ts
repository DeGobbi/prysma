import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProdutoCompletoDTO } from '../../models/produto/produto.model';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private apiUrl = 'http://localhost:8080/api/produtos'; // ajuste a rota conforme seu backend

  constructor(private http: HttpClient) {}

  criarProduto(produto: ProdutoCompletoDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/completo`, produto);
  }

  listarProdutos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}