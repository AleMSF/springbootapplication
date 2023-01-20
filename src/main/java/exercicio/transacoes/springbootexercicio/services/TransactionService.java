package exercicio.transacoes.springbootexercicio.services;

import exercicio.transacoes.springbootexercicio.domain.Balance;
import exercicio.transacoes.springbootexercicio.domain.Transaction;
import exercicio.transacoes.springbootexercicio.domain.TransactionDTO;
import exercicio.transacoes.springbootexercicio.domain.TransactionType;
import exercicio.transacoes.springbootexercicio.exception.BadRequestException;
import exercicio.transacoes.springbootexercicio.requests.TransactionPostRequestBody;
import exercicio.transacoes.springbootexercicio.requests.TransactionPutRequestBody;
import exercicio.transacoes.springbootexercicio.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private Balance balance;

    private final TransactionRepository transactionRepository;

    public Page<Transaction> listAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }


    public List<Transaction> listAllNonPageable() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findByTitle(String title) {
        return transactionRepository.findByTitle(title);
    }

    public Transaction findByIdOrThrowBadRequestException(int id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Transaction not found"));
    }

    public Transaction save(TransactionPostRequestBody transaction) {
        if (!(transaction.getType().equals(TransactionType.OUTCOME) //
                && getBalance().getTotal() < transaction.getValue())) {
        }
            return transactionRepository.save(Transaction.builder().title(transaction.getTitle())
                    .value(transaction.getValue())
                    .type(TransactionType.valueOf(transaction.getType()))
                    .build());

    }

    public void delete(int id) {
        transactionRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(TransactionPutRequestBody transactionPutRequestBody) {
        Transaction savedTransaction = findByIdOrThrowBadRequestException(transactionPutRequestBody.getId());
        Transaction transaction = Transaction.builder()
                .id(savedTransaction.getId())
                .title(transactionPutRequestBody.getTitle())
                .value(savedTransaction.getValue())
                .type(savedTransaction.getType())
                .build();

        transactionRepository.save(transaction);
    }

    public TransactionDTO listAllWithTotal() {
        Balance balance = new Balance();

        List<Transaction> all = transactionRepository.findAll();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionList(all);

        balance.setOutcome(Double.valueOf(0));
        balance.setIncome(Double.valueOf(0));


        for (Transaction transaction : all) {

            if (transaction.getType().equals(TransactionType.INCOME)) {
                balance.setIncome(balance.getIncome() + transaction.getValue());
            }
            if (transaction.getType().equals(TransactionType.OUTCOME)) {
                balance.setOutcome(balance.getOutcome() + transaction.getValue());
            }
            balance.setTotal(balance.getIncome() - balance.getOutcome());
        }

        transactionDTO.setBalance(balance);
        return transactionDTO;
    }

    public Balance getBalance() {
        List<Transaction> all = transactionRepository.findAll();

        for (Transaction transaction : all) {
            if (transaction.getType().equals(TransactionType.INCOME)) {
                balance.setIncome(balance.getIncome() + transaction.getValue());
            }
            if (transaction.getType().equals(TransactionType.OUTCOME)) {
                balance.setOutcome(balance.getOutcome() + transaction.getValue());
            }
        }

        return balance;
    }
}
