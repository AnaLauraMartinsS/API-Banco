package org.acme.ana;

import com.banco.exceptions.ContaInvalidaException;
import com.banco.models.ContaCorrente;
import com.banco.service.ContaCorrenteService;
import com.banco.service.ContaCorrenteServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/contaCorrente")
public class QuarkusResource {

    List<ContaCorrente> contasCorrentes = new ArrayList<>();
    ContaCorrenteService contaService = new ContaCorrenteServiceImpl(contasCorrentes);

    @GET
    @Path("/listarContas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListarContas() {
        return Response.ok(contasCorrentes).build();
    }

    @POST
    @Path("/criarConta/{nome}/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response CriarConta(@PathParam("nome") String nome, @PathParam("cpf") String cpf) {
        try {
            ContaCorrente contaCorrente = contaService.criarConta(nome, cpf);
            return Response.ok (contaCorrente).build();
        } catch (ContaInvalidaException exception) {
            return Response.status(400, "Conta inválida!!").build();
        } catch (Exception exception){
            return (null);
        }
    }

    @POST
    @Path("depositar/{numeroConta}/{valor}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response depositar( @PathParam("numeroConta") String numeroConta, @PathParam("valor") double valor) {
        try {
            contaService.depositar(numeroConta, valor);
            return Response.ok("O seu depósito foi realizado com sucesso!! Para a conta " + numeroConta).build();
        }catch (ContaInvalidaException exception){
            return Response.status(400, "Conta inválida! Por favor, certifique-se que o nº da conta está correto!!").build();
        }catch (Exception exception){
            return (null);
        }
    }

    @GET
    @Path("/saldo/{numeroConta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verSaldo(@PathParam("numeroConta" ) String numeroConta ) {
        ContaCorrente contaCorrente = contaService.getContaPorNumero(numeroConta);
        if(contaCorrente != null){
            return Response.ok ("O saldo da conta: "+ numeroConta +" é R$ = " + contaCorrente.getSaldo()).build();
        }else {
            return Response.status (400,"A sua conta não foi encontrada! Verifique o número da conta").build();
        }
    }

    @POST
    @Path("/sacar/{numeroConta}/{valor}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sacar(@PathParam("numeroConta") String numeroConta, @PathParam("valor") double valor) {
        try{
            contaService.sacar(numeroConta, valor);
            return Response.ok("O saque no valor de R$: " + valor + "\nFoi realizado com sucesso! \nNúmero da conta:" + numeroConta).build();
        }catch (ContaInvalidaException exception){
            return Response.status(400,"Conta inválida, verifique o número da conta!").build();
        }catch (Exception exception){
            return Response.status(400,"Ocorreu um erro ao realizar o saque da conta:" + numeroConta + "Por favor tente novamente").build();
        }
    }

    @PATCH
    @Path("transferir/{contaOrigem}/{contaDestino}/{valor}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferir (@PathParam("contaOrigem") String contaOrigem,
                              @PathParam("contaDestino") String contaDestino,
                              @PathParam("valor") double valor) {
        try {
            contaService.transferir(contaOrigem, contaDestino, valor);
            return Response.ok("A tranferência foi realizada com sucesso!! \nNo valor de " + valor + "\nDa conta: "+ contaOrigem + "\nPara: "+ contaDestino).build();
        }catch (ContaInvalidaException exception){
            return Response.status(400, "Conta inválida, verifique o número da conta!").build();
        } catch (Exception exception){
            return Response.status(400,"Ocorreu um erro ao realizar a transferencia. Por favor tente novamente!").build();
        }
    }
}
