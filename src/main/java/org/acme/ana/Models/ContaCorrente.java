package org.acme.ana.Models;

import org.acme.ana.Exceptions.SaldoInsuficienteException;

public class ContaCorrente extends ContaBancaria{

    private static final double TATXA_MENUTENCAO_CC = 0.01;
    private final double taxaManutencao;

    public ContaCorrente(String numeroConta, Double saldo, Cliente titular) {
        super(numeroConta, saldo, titular);
        this.taxaManutencao = TATXA_MENUTENCAO_CC;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        double taxa = valor * taxaManutencao;
        super.sacar(valor + taxa);
    }
}
