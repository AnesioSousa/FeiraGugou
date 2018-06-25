package model;

/**
 *
 * @author anesio
 */
public class Dados implements Comparable<Dados> {

    private String titulo;
    private int frequencia;

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public int getFrequencia() {return frequencia;}
    public void setFrequencia(int frequencia) {this.frequencia = frequencia;}

    @Override
    public String toString() {
        return "Dados{" + "titulo=" + titulo + ", frequencia=" + frequencia + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Dados) {
            Dados other = (Dados) obj;
            if (this.titulo.equals(other.getTitulo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Dados o) {
        if (this.getFrequencia() > o.getFrequencia()) {
            return - 1;
        }
        if (this.getFrequencia() < o.getFrequencia()) {
            return - -1;
        }
        return 0;/*this.titulo.compareTo(o.titulo);*/ // Só de exemplo. A ordem natural de paǵinas deve ser por relevancia, ou seja, por ocorrencia da palavra
    }
}
