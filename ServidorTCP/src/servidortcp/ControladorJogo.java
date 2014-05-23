/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidortcp;

import java.util.ArrayList;

/**
 *
 * @author jessica
 */
public class ControladorJogo {

    static ArrayList<Peca> pecasDisponiveis;
    static final int numeroPecasPorJogador = 6;
    static ArrayList<Jogador> jogadores;
    static Mesa mesa;

    ControladorJogo(ArrayList<Jogador> jogadores) {
        pecasDisponiveis = gerarListaTodasPecas();
        preencherMaoJogadores(jogadores);
        ControladorJogo.jogadores = jogadores;
        ControladorJogo.mesa = new Mesa();
    }

    public ArrayList<Peca> distribuirPecas() {
        ArrayList<Peca> pecasDoJogador = new ArrayList();

        for (int i = 0; i < numeroPecasPorJogador; i++) {
            int indice = (int) Math.floor(Math.random() * (pecasDisponiveis.size() - 1));
            pecasDoJogador.add(pecasDisponiveis.get(indice));
            pecasDisponiveis.remove(pecasDisponiveis.get(indice));
        }
        return pecasDoJogador;
    }

    static ArrayList<Peca> gerarListaTodasPecas() {
        ArrayList<Peca> pecas = new ArrayList();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                pecas.add(new Peca(i, j));
            }
        }
        return pecas;
    }

    private void preencherMaoJogadores(ArrayList<Jogador> jogadores) {
        for (Jogador jogador : jogadores) {
            jogador.pecasDoJogador = distribuirPecas();
        }
    }
    
    public static void inserirPecaMesa(Peca peca, String direita, Jogador j) {
        if (direita.equals("1")) {
            mesa.inserirPecaExtremidadeDireita(peca);
        } else {
            mesa.inserirPecaExtremidadeEsquerda(peca);
        }
        j.removerPeca(peca);
     }

    String imprimirPecasDisponiveis() {
        String pecasDisponiveisParaCompra="";
        for (Peca p : pecasDisponiveis) {
            pecasDisponiveisParaCompra+=p.toString()+",";
        }
        return pecasDisponiveisParaCompra;
    }

    void comprarPeca(Jogador jogadorQueJogouPeca, Peca peca) {
        jogadorQueJogouPeca.pecasDoJogador.add(peca);
        removerPecaDaListaDePecasDisponiveis(peca);
    }

    String quantidadeDePecasJogadores() {
        String quantidadePecasDeCadaJogador="";
        for (Jogador j : jogadores) {
            quantidadePecasDeCadaJogador+="Jogador "+j.id+": "+j.pecasDoJogador.size()+";";
        }
        return quantidadePecasDeCadaJogador;
        
    }

    private void removerPecaDaListaDePecasDisponiveis(Peca peca) {
        for(Peca p : pecasDisponiveis){
            if(p.equals(peca)){
                pecasDisponiveis.remove(p);
                break;
            }
    }
    }
}