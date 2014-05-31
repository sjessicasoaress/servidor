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
    int ordem;
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
        this.ordem = 1;
        this.id = cont;
        cont ++;
    }

    int getOrdem() {
        return this.ordem;
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
}
