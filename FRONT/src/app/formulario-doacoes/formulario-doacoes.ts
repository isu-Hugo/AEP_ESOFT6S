import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-formulario-doacoes',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './formulario-doacoes.html',
  styleUrls: ['./formulario-doacoes.css']
})
export class FormularioDoacoes {
  apiUrl = 'http://localhost:8080/api/doacoes';

  itemDoacao = '';
  categoria = 'Roupa';
  tamanho = '';
  estadoConservacao = 'Usado';
  obs = '';
  pontoColeta = '';
  estadoUf = '';
  cidade = '';

  passoAtual = 0;
  totalPassos = 8;
  enviando = false;
  concluido = false;

  bolhas = [
    { top: '6%', left: '15%', size: 190 },
    { top: '12%', left: '75%', size: 70 },
    { top: '48%', left: '4%', size: 60 },
    { top: '68%', left: '40%', size: 150 },
    { top: '32%', left: '85%', size: 110 },
    { top: '80%', left: '68%', size: 95 },
    { top: '85%', left: '12%', size: 45 }
  ];

  constructor(private http: HttpClient) {}

  passoValido() {
    if (this.passoAtual === 0) return this.itemDoacao.trim() !== '';
    if (this.passoAtual === 5) return this.estadoUf.trim() !== '';
    if (this.passoAtual === 6) return this.cidade.trim() !== '';
    return true;
  }

  proximoPasso() {
    if (this.passoValido()) this.passoAtual++;
  }

  passoAnterior() {
    this.passoAtual--;
  }

  publicarDoacao() {
    this.enviando = true;

    const doacao = {
      item_doacao: this.itemDoacao,
      categoria: this.categoria,
      tamanho: this.tamanho,
      estado_conservacao: this.estadoConservacao,
      obs: this.obs,
      ponto_coleta: this.pontoColeta,
      estado_uf: this.estadoUf,
      cidade: this.cidade
    };

    this.http.post(this.apiUrl, doacao).subscribe({
      next: () => {
        this.concluido = true;
        this.enviando = false;
      },
      error: () => {
        this.enviando = false;
      }
    });
  }
}
