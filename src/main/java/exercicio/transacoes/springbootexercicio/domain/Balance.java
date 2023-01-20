package exercicio.transacoes.springbootexercicio.domain;


import lombok.Data;

@Data
public class Balance {
    private Double income;
    private Double outcome;
    private Double total;
}
