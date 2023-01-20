package exercicio.transacoes.springbootexercicio.repository;

import exercicio.transacoes.springbootexercicio.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByTitle(String title);
}
