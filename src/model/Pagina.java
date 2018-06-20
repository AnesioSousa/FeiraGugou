package model;

/**
 *
 * @author Anésio
 */
public class Pagina {
    private String titulo;
    private int vezesAcessada;
    private int ocorrencias;

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public int getVezesAcessada() {return vezesAcessada;}
    public void setVezesAcessada(int vezesAcessada) {this.vezesAcessada = vezesAcessada;}
    public int getOcorrencias() {return ocorrencias;}
    public void setOcorrencias(int ocorrencias) {this.ocorrencias = ocorrencias;}

    @Override
    public String toString() {
        return "Pagina{" + "titulo=" + titulo + ", ocorrencias=" + ocorrencias + '}';
        //                                         PARA TESTES!!! RETIRAR DEPOIS
    }
    
}
