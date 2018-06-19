package model;

/**
 *
 * @author Anésio
 */
public class Pagina {
    private String titulo;
    private int vezesAcessada;

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public int getVezesAcessada() {return vezesAcessada;}
    public void setVezesAcessada(int vezesAcessada) {this.vezesAcessada = vezesAcessada;}

    @Override
    public String toString() {
        return "Pagina{" + "titulo=" + titulo + '}';
    }
 
}
