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
        ArrayList<Jogador> jogadoresOrdenados = definirOrdem(jogadores);
        ControladorJogo.jogadores = jogadoresOrdenados;
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
    
    private ArrayList<Jogador> definirOrdem(ArrayList<Jogador> jogadores){
        
        Jogador primeiroJogador = jogadores.get(0);
        
        ArrayList<Jogador> jogadoresOrdenados = new ArrayList();
    
        boolean encontrou = false;
        int i = 0, p = 6; // p é usada para formar as peças carrocas 6,6 5,5 4,4 3,3 2,2 1,1
        
        while(p>0)
        {
            while( (i<4) && (!encontrou) )
            {
                Peca carroca = new Peca (p,p);
    
                if(jogadores.get(i).contemPeca(carroca))
                {
                    primeiroJogador = jogadores.get(i);
                    encontrou = true;
                 }
                i++;
            }
            i=0;
            p--;
        }
        int posicao = jogadores.indexOf(primeiroJogador);
        int novoId = 0;
        for(i=posicao; i<4;i++)
        {
            jogadoresOrdenados.add(jogadores.get(i));
            jogadores.get(i).id = novoId;
            novoId++;
        }
        for(i=0;i<posicao;i++)
        {
            jogadoresOrdenados.add(jogadores.get(i));
            jogadores.get(i).id = novoId;
            novoId++;
        }
        return jogadoresOrdenados;
    }
    
    public ArrayList<Jogador> getJogadoresOrdenados ()
    {
        return ControladorJogo.jogadores;
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
