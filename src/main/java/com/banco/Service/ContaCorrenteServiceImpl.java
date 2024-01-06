package com.banco.Service;

import com.banco.Exceptions.ContaInvalidaException;
import com.banco.Exceptions.SaldoInsuficienteException;
import com.banco.Models.Cliente;
import com.banco.Models.ContaBancaria;
import com.banco.Models.ContaCorrente;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContaCorrenteServiceImpl implements ContaCorrenteService {
    private final List<ContaCorrente> contasCorrentes;
    private static final AtomicInteger contadorContas = new AtomicInteger(1);


    public ContaCorrenteServiceImpl(List<ContaCorrente> contasCorrentes) {
        this.contasCorrentes = contasCorrentes;
    }

    @Override
    public ContaCorrente getContaPorNumero(String numeroConta) {
        return contasCorrentes.stream()
                .filter(conta -> conta.getNumeroConta().equals(numeroConta))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void depositar(String numeroConta, double valor) throws ContaInvalidaException {
        ContaCorrente conta = getContaPorNumero(numeroConta);
        if (conta == null) {
            throw new ContaInvalidaException("Conta inválida. Por favor, verifique o número da conta.");}
        conta.depositar(valor);
    }

    @Override
    public void sacar(String numeroConta, double valor) throws ContaInvalidaException, SaldoInsuficienteException {
        ContaCorrente conta = getContaPorNumero(numeroConta);
        if (conta != null) {
            if (conta.getSaldo() >= valor) {
                conta.sacar(valor);
            } else {
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
            }
        } else {
            throw new ContaInvalidaException("Conta inválida. Por favor, verifique o número da conta.");
        }
    }
    @Override
    public void transferir(String contaOrigem, String contaDestino, double valor) throws ContaInvalidaException, SaldoInsuficienteException {
        ContaBancaria origem = getContaPorNumero(contaOrigem);
        ContaBancaria destino = getContaPorNumero(contaDestino);

        if (origem == null || destino == null) {
            System.out.println("Conta de origem ou destino não encontrada. Origem: " + origem + ", Destino: " + destino);
            throw new ContaInvalidaException("Conta de origem ou destino inválida. Por favor, verifique os números das contas.");
        }

        System.out.println("Conta de origem encontrada: " + origem.getNumeroConta());
        System.out.println("Conta de destino encontrada: " + destino.getNumeroConta());

        if (origem.getSaldo() >= valor) {
            origem.transferir(origem, destino, valor);
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência.");
        }
    }

    @Override
    public ContaCorrente criarConta(String nome, String cpf) throws ContaInvalidaException {
        if (nome == null || nome.isEmpty() || cpf == null || cpf.isEmpty()) {
            throw new ContaInvalidaException("Nome e CPF são obrigatórios.");
        }

        String numeroConta = gerarNumeroContaUnico();

        Cliente cliente = new Cliente(); //ele não está aceitando passar argumentos (nome, cpf)

        ContaCorrente novaConta =  new ContaCorrente(numeroConta, 0.0, cliente);
        this.contasCorrentes.add(novaConta);

        return novaConta;
    }
    private String gerarNumeroContaUnico() {
        return String.format("%05d", contadorContas.getAndIncrement());
    }
}
