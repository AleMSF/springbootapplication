package exercicio.transacoes.springbootexercicio.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TransactionPostRequestBody {
    @NotEmpty

    private String title;
    private Double value;
    private String type;
}
