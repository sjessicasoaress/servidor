package servidortcp;

/**
 *
 * @author jessica
 */
public class TipoMensagem
{
  static int ID_MENSAGEM_INICIAL = 0;
  //0#idDoJogador#pecasDoJogador#PecasDisponiveisParaCompra
  static int ID_MENSAGEM_INFORMAR_JOGADOR_DA_VEZ = 1;
  //1#idDoJogador
  static int ID_MENSAGEM_INFORMAR_JOGADA = 2;
  //#2#posicaoQueAPecaSeraInserida#Peca#QuantidadeDePecasJogadores
  //obs: posiç�o � 1 se for pra inserir na direita e 0 se for pra inserir na esquerda
  //falta incluir nesta mensagem a peça que a pessoa comprou
  static int ID_MENSAGEM_VENCEDOR_PARTIDA = 3;
  static int ID_MENSAGEM_VENCEDOR_JOGO = 4;
}
