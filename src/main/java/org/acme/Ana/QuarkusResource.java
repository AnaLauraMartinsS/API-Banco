package org.acme.Ana;

import com.banco.Exceptions.ContaInvalidaException;
import com.banco.Models.ContaCorrente;
import com.banco.Service.ContaCorrenteService;
import com.banco.Service.ContaCorrenteServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/contaCorrente")
public class QuarkusResource {

    List<ContaCorrente> contasCorrentes = new ArrayList<>();
    ContaCorrenteService contaService = new ContaCorrenteServiceImpl(contasCorrentes);

    @GET
    @Path("/listarContas")
    @Produces(MediaType.TEXT_PLAIN)
    public String ListarContas() {
        return contasCorrentes.toString();
    }

    @POST
    @Path("/criarConta/{nome}/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String CriarConta(@PathParam("nome") String nome, @PathParam("cpf") String cpf) {
        try {
            ContaCorrente contaCorrente = contaService.criarConta(nome, cpf);
            return ("A sua conta foi criada com sucesso!\n" + contaCorrente.toString());
        } catch (ContaInvalidaException exception) {
            return ("Conta inválida!!");
        } catch (Exception exception){
            return (null);
        }
    }

    @POST
    @Path("depositar/{numeroConta}/{valor}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String depositar( @PathParam("numeroConta") String numeroConta, @PathParam("valor") double valor) {
        try {
            contaService.depositar(numeroConta, valor);
            return ("O seu depósito foi realizado com sucesso!! \nPara a conta " + numeroConta);
        }catch (ContaInvalidaException exception){
            return ("Conta inválida! \nPor favor, certifique-se que o nº da conta está correto!!");
        }catch (Exception exception){
            return (null);
        }
    }

    @GET
    @Path("/saldo/{numeroConta}")
    @Produces(MediaType.TEXT_PLAIN)
    public String verSaldo(@PathParam("numeroConta" ) String numeroConta ) {
        ContaCorrente contaCorrente = contaService.getContaPorNumero(numeroConta);
        if(contaCorrente != null){
            return ("O saldo da conta: "+ numeroConta +" é R$ = " + contaCorrente.getSaldo());
        }else {
            return ("A sua conta não foi encontrada! Verifique o número da conta");
        }
    }

    @POST
    @Path("/sacar/{numeroConta}/{valor}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sacar(@PathParam("numeroConta") String numeroConta, @PathParam("valor") double valor) {
        try{
            contaService.sacar(numeroConta, valor);
            return ("O saque no valor de R$: " + valor + "\nFoi realizado com sucesso! \nNúmero da conta:" + numeroConta);
        }catch (ContaInvalidaException exception){
            return ("Conta inválida, verifique o número da conta!");
        }catch (Exception exception){
            return ("Ocorreu um erro ao realizar o saque da conta:" + numeroConta + "Por favor tente novamente");
        }
    }

    @PATCH
    @Path("transferir/{contaOrigem}/{contaDestino}/{valor}")
    @Consumes(MediaType.APPLICATION_JSON) // o tipo que vai ser consumido
    @Produces(MediaType.TEXT_PLAIN) // é o tipo que vai retornar
    public String transferir (@PathParam("contaOrigem") String contaOrigem,
                              @PathParam("contaDestino") String contaDestino,
                              @PathParam("valor") double valor) {
        try {
            contaService.transferir(contaOrigem, contaDestino, valor);
            return ("A tranferência foi realizada com sucesso!! \nNo valor de " + valor + "\nDa conta: "+ contaOrigem + "\nPara: "+ contaDestino);
        }catch (ContaInvalidaException exception){
            return ("Conta inválida, verifique o número da conta!");
        } catch (Exception exception){
            return ("Ocorreu um erro ao realizar a transferencia. Por favor tente novamente!");
        }
    }
}
