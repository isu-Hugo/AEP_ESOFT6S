package edu.unicesumar.aep.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoStatus {
    private DoacaoStatus status;
    private Instant dataHora;
    private String observacao;
}