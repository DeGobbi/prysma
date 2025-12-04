import { Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProdutoService } from '../../../services/produto/produto.service';
import { ProdutoCompletoDTO } from '../../../models/produto/produto.model';
import { CommonModule } from '@angular/common';
import { ImagemService } from '../../../services/imagem/imagem.service';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { empty, forkJoin } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-cadastro-produto',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './cadastro-produto.component.html',
  styleUrls: ['./cadastro-produto.component.scss']
})
export class CadastroProdutoComponent {
  
  produtoForm: FormGroup;
  
  // Usamos um Map para gerenciar arquivos por cor (index)
  uploadedFiles: Map<number, (File | null)[]> = new Map();
  
  // @ViewChildren para gerenciar os inputs de arquivo
  @ViewChildren('fileInput') fileInputs!: QueryList<ElementRef<HTMLInputElement>>;

  // Opções de Tamanho
  tamanhoOptions = [
    { id: 1, label: 'PP' }, { id: 2, label: 'P' }, { id: 3, label: 'M' }, 
    { id: 4, label: 'G' }, { id: 5, label: 'GG' },
    { id: 6, label: '35' }, { id: 7, label: '36' }, { id: 8, label: '37' },
    { id: 9, label: '38' }, { id: 10, label: '39' }, { id: 11, label: '40' },
    { id: 12, label: '41' }, { id: 13, label: '42' }, { id: 14, label: '43' },
    { id: 15, label: '44' }
  ];

  categorias = [
    { id: 1, label: 'Casual' },
    { id: 2, label: 'Esporte' },
    { id: 3, label: 'Inverno' },
    { id: 4, label: 'Verão' },
    { id: 5, label: 'Academia' }
  ];

  // Mapeamento de Categorias (para exibir o label no HTML)
  categoriaMap: { [key: number]: string } = {
    1: 'Casual', 2: 'Esporte', 3: 'Inverno', 4: 'Verão', 5: 'Academia'
  };

  modalAberto = false;
corSelecionada: number | null = null;
indiceImagemAtual = 0;
imagensPorCor: { [key: number]: { file: File | null; url: string }[] } = {};



  constructor(
    private fb: FormBuilder,
    private produtoService: ProdutoService, 
    private imagemService: ImagemService,
    private snackBar: MatSnackBar) {
    
    // Inicialização do Formulário com a nova estrutura global
    this.produtoForm = this.fb.group({
      nome: ['', Validators.required],
      descricao: ['', Validators.required],
      referencia: [''],
      preco: [null, [Validators.required, Validators.min(0)]],
      generoId: [null, Validators.required],
      categoriaIds: this.fb.control([], Validators.required),
      cores: this.fb.array([]) // começamos vazio e adicionamos via botão
});
  }

  // --- GETTERS PARA FACILITAR O ACESSO AOS FORM ARRAYS ---

  get categoriaIds(): FormArray {
    return this.produtoForm.get('categoriaIds') as FormArray;
  }

  get cores(): FormArray {
    return this.produtoForm.get('cores') as FormArray;
  }

  getImagens(corIndex: number) {
    return this.cores.at(corIndex).get('imagens') as FormArray;
  }

  getEstoques(corIndex: number) {
    return this.cores.at(corIndex).get('estoques') as FormArray;
  }

  removerCategoria(catId: number): void {
    const label = this.getCategoryLabel(catId);
  const confirmar = confirm(`Remover a categoria "${label}"?`);
  if (!confirmar) return;

  const categoriaControl = this.produtoForm.get('categoriaIds');
  if (categoriaControl) {
    const atualizadas = (categoriaControl.value as number[]).filter(id => id !== catId);
    categoriaControl.setValue(atualizadas);
    categoriaControl.markAsDirty();
  }
  }

  // --- MÉTODOS DE CRIAÇÃO DE GRUPOS E CONTROLES ---

  createCorGroup(): FormGroup {
    return this.fb.group({
      corNome: ['', Validators.required],
      estoques: this.fb.array([]),
      imagens: this.fb.array([]) 
    });
  }

  // --- MÉTODOS DE GERENCIAMENTO DE FORM ARRAYS ---

  // Adiciona um ID de categoria ao FormArray global
  addCategoriaId(id: number): void {
    const idNumber = Number(id); // Garante que é um número, pois o #novaCategoria.value pode ser string
    const existingIds = this.categoriaIds.value as number[];
    
    // Verifica se o ID é válido e se já não foi adicionado
    if (idNumber && !existingIds.includes(idNumber)) {
      this.categoriaIds.push(this.fb.control(idNumber));
    }
  }

  // Remove um ID de categoria (Você deve implementar um botão de remoção no HTML)
  removeCategoriaId(id: number): void {
      const index = this.categoriaIds.value.findIndex((catId: number) => catId === id);
      if (index !== -1) {
          this.categoriaIds.removeAt(index);
      }
  }

  // Retorna o label da categoria (útil para o HTML)
  getCategoryLabel(id: number): string {
    return this.categoriaMap[id] || 'Categoria Desconhecida';
  }

  addCor(): void {
  this.cores.push(this.createCorGroup());
  const newIndex = this.cores.length - 1;
  this.imagensPorCor[newIndex] = []; // garante novo array independente
}
  
  removeCor(index: number): void {
  // Revoga URLs das imagens da cor removida
  const imagens = this.imagensPorCor[index];
  if (imagens) {
    imagens.forEach(img => URL.revokeObjectURL(img.url));
  }

  // Remove o grupo do FormArray
  this.cores.removeAt(index);

  // Remove apenas as imagens dessa cor
  delete this.imagensPorCor[index];

  // Reindexa corretamente sem perder outras imagens
  const novasImagensPorCor: { [key: number]: { file: File | null; url: string }[] } = {};
  let novoIndex = 0;
  Object.keys(this.imagensPorCor)
    .sort((a, b) => Number(a) - Number(b))
    .forEach((chaveAntiga) => {
      novasImagensPorCor[novoIndex] = this.imagensPorCor[Number(chaveAntiga)];
      novoIndex++;
    });

  this.imagensPorCor = novasImagensPorCor;
}

  addEstoque(corIndex: number, tamanhoId: number, quantidade: number): void {
    const estoques = this.getEstoques(corIndex);
    // Validação básica para evitar duplicatas de tamanho
    const exists = estoques.controls.some(c => c.value.tamanhoId === tamanhoId);
    
    if (tamanhoId && quantidade > 0 && !exists) {
        estoques.push(this.fb.group({
            tamanhoId: [tamanhoId, Validators.required],
            quantidade: [quantidade, Validators.required]
        }));
    } else if (exists) {
        alert('Este tamanho já foi adicionado para esta cor.');
    }
  }

  // Remove estoque (Implemente um botão no HTML)
  removeEstoque(corIndex: number, estoqueIndex: number): void {
      this.getEstoques(corIndex).removeAt(estoqueIndex);
  }

  getTamanhoLabel(id: number): string {
  const tamanho = this.tamanhoOptions.find(t => t.id === id);
  return tamanho ? tamanho.label : `ID ${id}`;
}

  // --- MÉTODOS DE UPLOAD DE IMAGEM ---

  onFileSelected(event: Event, corIndex: number) {
  const input = event.target as HTMLInputElement;
  const files = input.files;
  if (!files || files.length === 0) return;

  const filesArray = Array.from(files);

  // garante array para a cor
  if (!this.imagensPorCor[corIndex]) this.imagensPorCor[corIndex] = [];

  const formData = new FormData();
  filesArray.forEach(file => formData.append('files', file));

  this.imagemService.uploadImagensTemporarias(formData).subscribe({
    next: (urls: string[]) => {
      // garante que o array existe (pode ter sido removido enquanto o upload acontecia)
      if (!this.imagensPorCor[corIndex]) this.imagensPorCor[corIndex] = [];

      const count = Math.min(urls.length, filesArray.length);
      for (let i = 0; i < count; i++) {
        this.imagensPorCor[corIndex].push({
          file: filesArray[i],
          url: urls[i]
        });
      }
    },
    error: err => {
      this.snackBar.open('Erro ao enviar imagem temporária.', 'Fechar', { duration: 3000 });
    }
  });

  // NÃO logar this.imagensPorCor[corIndex] aqui — a resposta ainda não chegou
  input.value = '';
}
  
  // Método para acionar o input file correto
  triggerFileInput(corIndex: number): void {
      // Usa QueryList para encontrar o input file específico
      const inputElement = this.fileInputs.toArray()[corIndex].nativeElement;
      inputElement.click();
  }

  // --- MÉTODO DE SALVAR FINAL ---
  
  salvar(): void {
  if (this.produtoForm.invalid) {
    this.produtoForm.markAllAsTouched();
    this.snackBar.open('Preencha todos os campos obrigatórios antes de salvar.', 'Fechar', {
      duration: 4000,
      panelClass: ['snackbar-error']
    });
    return;
  }
  
  // Obtem o valor do form
  const produto = this.produtoForm.value;

  // Verifica se há imagens para upload
  const uploadRequests = this.cores.controls.map((_, index) => {
  const files = (this.imagensPorCor[index]
    ?.map(img => img.file)
    .filter((f): f is File => f !== null)) || [];

  if (files.length > 0) {
    return this.imagemService.uploadImagens(files);
  }

  return null;
}).filter(req => req !== null);

  if (uploadRequests.length === 0) {
    this.enviarProduto(produto);
  } else {
    // Upload múltiplo em paralelo (assíncrono)
    forkJoin(uploadRequests).subscribe({
      next: (urlsPorCor: string[][]) => {
        const produtoFormatado = {
          ...produto,
          generoId: Number(produto.generoId),
          categoriaIds: produto.categoriaIds.map((id: any) => Number(id)),
          cores: produto.cores.map((cor: any, index: number) => ({
            ...cor,
            imagens: urlsPorCor[index] || []
          }))
        };

        this.enviarProduto(produtoFormatado);
      },
      error: err => {
        console.error('Erro ao enviar imagens:', err);
      }
    });
  }
}

private enviarProduto(produtoFormatado: any) {
  this.produtoService.criarProduto(produtoFormatado).subscribe({
    next: res => {
      console.log('Produto criado com sucesso', res);
      this.produtoForm.reset();
      this.imagensPorCor = {};
    },
    error: err => {
      console.error('Erro ao criar produto', err);
    }
  });
}

  abrirModalImagens(corIndex: number) {
  this.corSelecionada = corIndex;
  this.indiceImagemAtual = 0;
  this.modalAberto = true;
}

fecharModal() {
  this.modalAberto = false;
  this.corSelecionada = null;
}

proximaImagem() {
  if (this.corSelecionada === null) return;
  const imagens = this.imagensPorCor[this.corSelecionada];
  if (imagens && imagens.length > 0) {
    this.indiceImagemAtual = (this.indiceImagemAtual + 1) % imagens.length;
  }
}

imagemAnterior() {
  if (this.corSelecionada === null) return;
  const imagens = this.imagensPorCor[this.corSelecionada];
  if (imagens && imagens.length > 0) {
    this.indiceImagemAtual =
      (this.indiceImagemAtual - 1 + imagens.length) % imagens.length;
  }
}

removerImagem(corIndex: number, imgIndex: number) {
  this.imagensPorCor[corIndex].splice(imgIndex, 1);
  if (this.modalAberto && this.imagensPorCor[corIndex].length === 0) {
    this.fecharModal();
  } else if (
    this.modalAberto &&
    this.indiceImagemAtual >= this.imagensPorCor[corIndex].length
  ) {
    this.indiceImagemAtual = Math.max(0, this.indiceImagemAtual - 1);
  }
}

ngOnInit() {
  this.produtoForm = this.fb.group({
    nome: ['', Validators.required],
    descricao: ['', Validators.required],
    referencia: [''],
    preco: [null, [Validators.required, Validators.min(0)]],
    generoId: [null, Validators.required],
    categoriaIds: this.fb.control([], Validators.required),
    cores: this.fb.array([])
  });

  // Adiciona uma cor de exemplo
  const cor1 = this.createCorGroup();
  this.cores.push(cor1);

  // Preenche tudo automaticamente
  this.produtoForm.patchValue({
    nome: 'Camiseta para treino',
    descricao: 'Camiseta para academia',
    referencia: 'ref-123',
    preco: 120,
    generoId: 1,
    categoriaIds: [2, 4, 5],
    cores: [
      {
        corNome: 'Azul',
        estoques: [],
        imagens: []
      }
    ]
  });
}
  
}

