package servidortcp;

/**
 *
 * @author jessica
 */
public class Peca {

    int parteEsquerda;
    int parteDireita;

    Peca(int parteEsquerda, int parteDireita) {
        this.parteEsquerda = parteEsquerda;
        this.parteDireita = parteDireita;
    }

    Peca(String partesPeca) {
        String[] itensPartesPeca = partesPeca.split("|");
        this.parteEsquerda = Integer.parseInt(itensPartesPeca[0]);
        this.parteDireita = Integer.parseInt(itensPartesPeca[2]);
    }

    public String toString() {
        return this.parteEsquerda + "|" + this.parteDireita;
    }

    public boolean equals(Peca p) {
        if (((this.parteDireita == p.parteDireita) && (this.parteEsquerda == p.parteEsquerda)) || ((this.parteEsquerda == p.parteDireita) && (this.parteEsquerda == p.parteDireita))) {
            return true;
        }
        return false;
    }
}
