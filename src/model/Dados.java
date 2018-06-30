package model;

/**
 * Classe que cria objetos que são caracteristicas de paginas e ocorrencias de palavras nelas.
 * A classe implementa a interface Comparable para que seus objetos se tenham uma ordem natural e se tornem comparáveis.
 * @author Anésio Sousa
 */
public class Dados implements Comparable<Dados> {

    private String titulo;
    private int quantidade;

    /**
     * Retorna o título que esse objeto dados armazena.
     * @return string contendo o título.
     */
    public String getTitulo() {return titulo;}

    /**
     * Dita qual titulo esse objeto dados irá passar a armazenar.
     * @param titulo string contendo o título.
     */
    public void setTitulo(String titulo) {this.titulo = titulo;}

    /**
     * Retorna o valor quantidade.
     * @return int contendo a quantidade.
     */
    public int getQuantidade() {return quantidade;}

    /**
     * Dita qual quantidade esse objeto dados irá passar a armazenar.
     * @param quantidade
     */
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}

    @Override
    public String toString() {
        return "Titulo=" + titulo + ", Quantidade=" + quantidade;
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
        if (this.getQuantidade() > o.getQuantidade()) {
            return - 1;
        }
        if (this.getQuantidade() < o.getQuantidade()) {
            return - -1;
        }
        return 0;
    }
}
