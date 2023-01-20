package exercicio.transacoes.springbootexercicio.domain;

import lombok.Data;

import java.util.List;

@Data
public class TransactionDTO {

    private List<Transaction> transactionList;
    private Balance balance;
}
