package servidortcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author jessica
 */
public class ServidorTCP {

    public static ArrayList<Jogador> jogadores;
    ControladorJogo c;
    int pontuacaoPartida = 0;
    int pontuacaoJogo = 0;
    int quantidadeJogadores = 0;

    ServidorTCP() throws IOException {
        ServerSocket socketDeEscuta = new ServerSocket(40000);
        jogadores = new ArrayList();
        try{
        while(true) {
            Socket socketDeConexao = socketDeEscuta.accept();
            this.quantidadeJogadores ++;
            if (this.quantidadeJogadores < 5) {
                Scanner entrada = new Scanner(socketDeConexao.getInputStream());
                PrintWriter saida = new PrintWriter(socketDeConexao.getOutputStream());
                jogadores.add(new Jogador(socketDeConexao, entrada, saida));
                if (jogadores.size() == 4) {
                    this.c = new ControladorJogo(jogadores);
                    iniciarJogo();
                }
            } else {
                socketDeConexao.close();
            }
        }}
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Erro:" + ex.getMessage());
        }
    }

    private void iniciarJogo() throws IOException {
        enviarMensagemInicial(this.c);
        //TO DO: 1)colocar a ordenaçao dos jogadores de acordo com a maior peça
        // 2)Informar aos jogadores quais peças o jogador comprou para que eles as removam da lista de peças disponiveis para compra
        // 3) n�o considerei comprar uma peça e depois passar a vez -> tratar este caso!
        // 4) reiniciar partida ap�s vitoria de um participante
        while(true) {
            for (int i = 0; i < 4; i = (i + 1) % 4) {
                informarJogadorDaVez(i);
                String jogada = ((Jogador) jogadores.get(i)).entrada.nextLine();
                System.out.println("Jogada recebida pelo servidor:" + jogada);
                String[] itensJogada = jogada.split("#");
                if (itensJogada.length > 0) {
                    this.c.inserirPecaMesa(new Peca(itensJogada[1]), itensJogada[0], jogadores.get(i));
                    informarJogadaParaTodosOsJogadores(itensJogada, jogadores.get(i));
                    if (jogadores.get(i).pecasDoJogador.isEmpty()) {
                        informarVitoriaPartidaParaTodosOsJogadores(jogadores.get(i).id, itensJogada);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ServidorTCP();
    }

    private void enviarMensagemAoJogador(Jogador j, String mensagem) throws IOException {
        j.saida.println(mensagem);
        j.saida.flush();
    }

    private void informarJogadaParaTodosOsJogadores(String[] itensJogada, Jogador jogadorQueJogouPeca) throws IOException {
        String posicao = itensJogada[0];
        Peca p = new Peca(itensJogada[1]);
        String[] pecasCompradas;
        if (itensJogada.length > 3) {
            pecasCompradas = itensJogada[3].split(",");
            for (int i = 0; i < pecasCompradas.length; i++) {
                this.c.comprarPeca(jogadorQueJogouPeca, new Peca(pecasCompradas[i]));
            }
        }
        for (Jogador j : jogadores) {
            enviarMensagemAoJogador(j, TipoMensagem.ID_MENSAGEM_INFORMAR_JOGADA + "#" + posicao + "#" + p.toString() + "#" + this.c.quantidadeDePecasJogadores());
        }
    }

    private void informarVitoriaPartidaParaTodosOsJogadores(int idJogador, String[] itensJogada)
            throws IOException {
        calcularPontuacaoPartida(itensJogada);
        for (Jogador j : jogadores) {
            enviarMensagemAoJogador(j, TipoMensagem.ID_MENSAGEM_VENCEDOR_PARTIDA + "#" + idJogador + "#" + this.pontuacaoPartida);
        }
    }

    public void enviarMensagemInicial(ControladorJogo c)
            throws IOException {
        for (Jogador j : jogadores) {
            enviarMensagemAoJogador(j, TipoMensagem.ID_MENSAGEM_INICIAL + "#" + j.id + "#" + j
                    .imprimirPecasJogador() + "#" + c.imprimirPecasDisponiveis());
        }
    }

    public void informarJogadorDaVez(int idJogadorDaVez)
            throws IOException {
        for (Jogador j : jogadores) {
            enviarMensagemAoJogador(j, TipoMensagem.ID_MENSAGEM_INFORMAR_JOGADOR_DA_VEZ + "#" + idJogadorDaVez);
        }
    }

    private void calcularPontuacaoPartida(String[] itensJogada) {
        Peca pecaExtremidadeEsquerda = new Peca(itensJogada[2].split(",")[0]);
        Peca pecaExtremidadeDireita = new Peca(itensJogada[2].split(",")[1]);
        Peca ultimaPeca = new Peca(itensJogada[1]);
        String posicao = itensJogada[0];
        boolean carroca = false;
        boolean serviuParaOsDoisLados = false;
        if (ultimaPeca.parteDireita == ultimaPeca.parteEsquerda) {
            carroca = true;
        }
        if (((posicao.equals("1")) && (ultimaPeca.parteDireita == pecaExtremidadeEsquerda.parteEsquerda)) || ((posicao.equals("0")) && (ultimaPeca.parteEsquerda == pecaExtremidadeDireita.parteDireita))) {
            serviuParaOsDoisLados = true;
        }
        if ((carroca) && (serviuParaOsDoisLados)) {
            this.pontuacaoPartida = 4;
        } else if (serviuParaOsDoisLados) {
            this.pontuacaoPartida = 3;
        } else if (carroca) {
            this.pontuacaoPartida = 2;
        } else {
            this.pontuacaoPartida = 1;
        }
    }
}
