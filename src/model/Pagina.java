package model;

import java.util.Objects;

/**
 *
 * @author Anésio
 */
public class Pagina implements Comparable<Pagina>{
    private String titulo;
    private int vezesAcessada;
    private long info;

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public int getVezesAcessada() {return vezesAcessada;}
    public void setVezesAcessada(int vezesAcessada) {this.vezesAcessada = vezesAcessada;}
    public long getInfo() {return info;}
    public void setInfo(long info) {this.info = info;}

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
