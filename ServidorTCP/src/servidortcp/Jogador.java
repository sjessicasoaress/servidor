package servidortcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jessica
 */
public class Jogador {

    ArrayList<Peca> pecasDoJogador;
    boolean podeJogar;
    public Socket socket;
    Scanner entrada;
    int pontuacao;
    PrintWriter saida;
    static int cont = 0;
    char equipe;
    int id;
    
    Jogador() {
        this.podeJogar = false;
        this.pecasDoJogador = null;
    }

    Jogador(ArrayList<Peca> pecasDoJogador) {
        this.podeJogar = false;
        this.pecasDoJogador = pecasDoJogador;
    }

    Jogador(Socket socketDeConexao, Scanner entrada, PrintWriter saida) throws IOException {
        this.socket = socketDeConexao;
        this.entrada = entrada;
        this.saida = saida;
        System.out.println("Nova conexão com o cliente de IP: " + this.socket.getInetAddress().getHostAddress() + ", " + this.socket.getPort());
        this.id = cont;
        
        //se o id for par, vai ser da equipe A
        //if(this.id%2==0)
          //  this.equipe='A';
        //else
          //  this.equipe='B';
        
        cont ++;
    }

    String imprimirPecasJogador() {
        String listaPecas = "";
        for (Peca peca : this.pecasDoJogador) {
            listaPecas = listaPecas + peca.toString() + ",";
        }
        return listaPecas;
    }

    void removerPeca(Peca p) {
        for (Peca peca : this.pecasDoJogador) {
            if (peca.equals(p)) {
                this.pecasDoJogador.remove(peca);
                break;
            }
        }
    }
    
    boolean contemPeca(Peca p){
   
        for(Peca peca : this.pecasDoJogador ){


            if((peca.parteDireita==p.parteDireita)&&(peca.parteEsquerda==p.parteEsquerda)){
//                System.out.println("Peca do jogador:"+peca.toString()+" carroça: "+p.toString());
                return true;
                
            }
        }
         return false;
        
    }
}
