package org.acme.ana.Models;

import org.acme.ana.Exceptions.SaldoInsuficienteException;

public class ContaBancaria {
    private String numeroConta;
    private Double saldo;
    private Cliente titular;
    private static final double TAXA_DE_TRANSFERENCIA = 0.001;

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public ContaBancaria(String numeroConta, Double saldo, Cliente titular) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.titular = titular;
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque inválido.");
        }
        if (saldo >= valor) {
            saldo -= valor;
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
        }
    }

    public void depositar(double valor){
        saldo += valor;
    }

    public void transferir(ContaBancaria origem, ContaBancaria destino, double valor) throws SaldoInsuficienteException {
        if(origem.getSaldo() < valor){
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência.");
        }
        origem.setSaldo(origem.getSaldo() - valor - (valor * TAXA_DE_TRANSFERENCIA));
        destino.setSaldo(destino.getSaldo() + valor);
    }

    @Override
    public String toString() {
        return "ContaBancaria{" +
                "Numero da Conta='" + numeroConta + '\'' +
                ", Saldo=" + saldo +
                ", Titular=" + titular +
                '}';
    }
}
