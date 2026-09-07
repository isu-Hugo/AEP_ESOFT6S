import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormularioDoacoes } from './formulario-doacoes/formulario-doacoes';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FormularioDoacoes],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('front-app');
}
