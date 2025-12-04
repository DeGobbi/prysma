import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImagemService {
  private apiUrl = 'http://localhost:8080/api/imagens';

  constructor(private http: HttpClient) {}

  uploadImagens(files: File[]): Observable<string[]> {
    const formData = new FormData();
    files.forEach(file => formData.append('files', file));
    return this.http.post<string[]>(`${this.apiUrl}/upload`, formData);
  }

  uploadImagensTemporarias(filesOrFormData: File[] | FormData) : Observable<string[]> {
  // Se já for um FormData, usamos direto; caso contrário, montamos o FormData a partir do array de Files
  if (filesOrFormData instanceof FormData) {
    return this.http.post<string[]>(`${this.apiUrl}/upload/temp`, filesOrFormData);
  }

  const formData = new FormData();
  filesOrFormData.forEach(file => formData.append('files', file));
  return this.http.post<string[]>(`${this.apiUrl}/upload/temp`, formData);
}
}