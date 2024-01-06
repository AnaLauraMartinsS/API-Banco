package com.banco.Service;

import com.banco.Exceptions.ContaInvalidaException;
import com.banco.Models.ContaCorrente;
import com.banco.Exceptions.SaldoInsuficienteException;

public interface ContaCorrenteService {
    ContaCorrente getContaPorNumero(String numeroConta);
    void depositar(String numeroConta, double valor) throws ContaInvalidaException;
    void sacar(String numeroConta, double valor) throws ContaInvalidaException, SaldoInsuficienteException;
    void transferir(String contaOrigem, String contaDestino, double valor) throws ContaInvalidaException, SaldoInsuficienteException;
    ContaCorrente criarConta(String nome, String cpf) throws ContaInvalidaException;
}
