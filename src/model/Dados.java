package model;

/**
 * Classe que cria objetos que são caracteristicas de paginas e ocorrencias de palavras nelas.
 * A classe implementa a interface Comparable para que seus objetos se tenham uma ordem natural e se tornem comparáveis.
 * @author Anésio Sousa
 */
public class Dados implements Comparable<Dados> {

    private String titulo;
    private int frequencia;

    /**
     * Retorna o título da página que esse objeto dados armazena.
     * @return string contendo o título.
     */
    public String getTitulo() {return titulo;}

    /**
     * Dita qual titulo de página esse objeto dados irá passar a armazenar.
     * @param titulo string contendo o título.
     */
    public void setTitulo(String titulo) {this.titulo = titulo;}

    /**
     * Retorna a frequência de uma palavra em determinada página.
     * @return int contendo a frequencia.
     */
    public int getFrequencia() {return frequencia;}

    /**
     * Dita qual frequencia esse objeto dados irá passar a armazenar.
     * @param frequencia
     */
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
        return 0;
    }
}
