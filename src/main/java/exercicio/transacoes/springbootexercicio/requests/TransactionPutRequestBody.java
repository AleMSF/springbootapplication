package exercicio.transacoes.springbootexercicio.requests;

import lombok.Data;

@Data
public class TransactionPutRequestBody {
    private Integer id;
    private String title;
    private Integer value;
    private String type;
}
