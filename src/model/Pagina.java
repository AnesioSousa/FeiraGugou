package model;

/**
 * Classe que cria objetos que são caracteristicas de arquivos de texto.
 * A classe implementa a interface Comparable para que seus objetos se tenham uma ordem natural e se tornem comparáveis.
 * @author Anésio Sousa
 */
public class Pagina implements Comparable<Pagina>{
    private String titulo;
    private int vezesAcessada;
    private long info;
    private boolean modified;

    /**
     * Retorna o título desta página.
     * @return string que armazena o título.
     */
    public String getTitulo() {return titulo;}

    /**
     * Dita qual titulo essa página irá passar a ter.
     * @param titulo string contendo o título.
     */
    public void setTitulo(String titulo) {this.titulo = titulo;}

    /**
     * Retorna a quantidade de vezes que essa página foi acessada.
     * @return int que armazena a quantidade.
     */
    public int getVezesAcessada() {return vezesAcessada;}

    /**
     * Dita quantas vezes essa página foi acessada.
     * @param vezesAcessada int com o valor;
     */
    public void setVezesAcessada(int vezesAcessada) {this.vezesAcessada = vezesAcessada;}

    /**
     * Retorna a um long com informações da última vez que essa página foi modificada.
     * @return long contendo as informações.
     */
    public long getInfo() {return info;}

    /**
     * Dita a informação da última vez que essa página foi modificada.
     * @param info long contendo o valor.
     */
    public void setInfo(long info) {this.info = info;}

    /**
     * Retorna a informação de se esta pagina foi ou não modificada.
     * @return variável boolean contendo a informação.
     */
    public boolean isModified() {return modified;}

    /**
     * Dita se esta página foi modificada ou não.
     * @param modified boolean contendo a informação.
     */
    public void setModified(boolean modified) {this.modified = modified;}
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Pagina) {
            Pagina a = (Pagina) obj;
            if (this.getTitulo().equals(a.getTitulo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Pagina o) {
        return this.getTitulo().compareTo(o.getTitulo());
    }

}
