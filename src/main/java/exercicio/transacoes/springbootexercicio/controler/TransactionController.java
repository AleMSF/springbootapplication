package exercicio.transacoes.springbootexercicio.controler;

import exercicio.transacoes.springbootexercicio.domain.Transaction;
import exercicio.transacoes.springbootexercicio.domain.TransactionDTO;
import exercicio.transacoes.springbootexercicio.requests.TransactionPostRequestBody;
import exercicio.transacoes.springbootexercicio.requests.TransactionPutRequestBody;
import exercicio.transacoes.springbootexercicio.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
@Log4j2
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionsService;
    private TransactionDTO transactionDTO;

    @GetMapping
    public TransactionDTO transacions(Pageable pageable) {
        return transactionsService.listAllWithTotal();
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Transaction>> listAll() {
        return ResponseEntity.ok(transactionsService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable int id) {
        return ResponseEntity.ok(transactionsService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Transaction>> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok(transactionsService.findByTitle(title));
    }

    @PostMapping
    public ResponseEntity<Transaction> save(@RequestBody @Valid TransactionPostRequestBody transaction) {
        return new ResponseEntity<>(transactionsService.save(transaction), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        transactionsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody TransactionPutRequestBody transactionPutRequestBody) {
        transactionsService.replace(transactionPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
