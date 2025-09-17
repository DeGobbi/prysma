import { Component, OnInit } from '@angular/core';
import { CadastroUsuarioService } from '../../../services/usuario/cadastro-usuario.service';
import { FormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cadastro-usuario',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  providers: [CadastroUsuarioService],
  templateUrl: './cadastro-usuario.component.html',
  styleUrl: './cadastro-usuario.component.scss'
})

export class CadastroUsuarioComponent {
  cadastroForm: FormGroup;
  mensagem: string = '';

  constructor(private fb: FormBuilder, private usuarioService: CadastroUsuarioService) {
    this.cadastroForm = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required, Validators.minLength(6)]],
      confirmarSenha: ['', Validators.required]
    }, { validators: this.senhasDevemCoincidir });
  }

  senhasDevemCoincidir(group: FormGroup) {
    const senha = group.get('senha')?.value;
    const confirmarSenha = group.get('confirmarSenha')?.value;
    return senha === confirmarSenha ? null : { senhasDiferentes: true };
  }

  onSubmit() {
    if (this.cadastroForm.valid) {
      const { nome, email, senha } = this.cadastroForm.value;

      this.usuarioService.criarUsuario({ nome, email, senha }).subscribe({
        next: (usuario) => {
          this.mensagem = 'Usuário ${usuario.nome} cadastrado com sucesso!';
          this.cadastroForm.reset();
        },
        error: (err) => {
          console.error('Erro ao cadastrar usuário:', err);
          this.mensagem = 'Erro ao cadastrar usuário. Tente novamente.';
        }
      });
    } else {
      this.cadastroForm.markAllAsTouched();
    }
  }
}