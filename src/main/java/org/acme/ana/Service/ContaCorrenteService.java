package org.acme.ana.Service;

import org.acme.ana.Exceptions.ContaInvalidaException;
import org.acme.ana.Exceptions.SaldoInsuficienteException;
import org.acme.ana.Models.ContaCorrente;

public interface ContaCorrenteService {
    ContaCorrente getContaPorNumero(String numeroConta);
    void depositar(String numeroConta, double valor) throws ContaInvalidaException;
    void sacar(String numeroConta, double valor) throws ContaInvalidaException, SaldoInsuficienteException;
    void transferir(String contaOrigem, String contaDestino, double valor) throws ContaInvalidaException, SaldoInsuficienteException;
    ContaCorrente criarConta(String nome, String cpf) throws ContaInvalidaException;
}
