export interface ProdutoEstoqueDTO {
  quantidade: number;
  tamanhoId: number;
}

export interface ProdutoCorDTO {
  corNome: string;
  estoques: ProdutoEstoqueDTO[];
  imagens: string[];
}

export interface ProdutoCompletoDTO {
  nome: string;
  descricao: string;
  preco: number;
  genero: string;
  categorias: string[];
  cores: ProdutoCorDTO[];
}