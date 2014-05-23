package servidortcp;

import java.util.ArrayList;

/**
 *
 * @author jessica
 */
public class Mesa {

    ArrayList<Peca> pecasDaMesa;

    Mesa() {
        this.pecasDaMesa = new ArrayList();
    }

    public void inserirPeca(Peca peca) {
        this.pecasDaMesa.add(0, peca);
    }

    public void inserirPecaExtremidadeEsquerda(Peca peca) {
        inserirPeca(peca);
    }

    public void inserirPecaExtremidadeDireita(Peca peca) {
        if (this.pecasDaMesa.isEmpty()) {
            inserirPeca(peca);
        } else {
            this.pecasDaMesa.add(this.pecasDaMesa.size() - 1, peca);
        }
    }

    public String toString() {
        String pecasMesa = "";
        for (Peca p : this.pecasDaMesa) {
            pecasMesa = pecasMesa + p.toString() + ",";
        }
        return pecasMesa;
    }
}
