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
        //int pos = titulo.indexOf(".");
        //String nome = titulo.substring(0, pos); 
        return "Pagina{" + "titulo=" + titulo /*nome*/ + ", ocorrencias=" + ocorrencias + '}';
        //                                         PARA TESTES!!! RETIRAR DEPOIS
    }
    
}
