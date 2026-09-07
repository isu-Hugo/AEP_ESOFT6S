import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioDoacoes } from './formulario-doacoes';

describe('FormularioDoacoes', () => {
  let component: FormularioDoacoes;
  let fixture: ComponentFixture<FormularioDoacoes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioDoacoes],
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioDoacoes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
