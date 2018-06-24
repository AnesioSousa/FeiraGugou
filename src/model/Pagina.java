package model;

import java.util.Objects;

/**
 *
 * @author Anésio
 */
public class Pagina{
    private String titulo;
    private int vezesAcessada;
    private long info;
    private boolean isModified;

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public int getVezesAcessada() {return vezesAcessada;}
    public void setVezesAcessada(int vezesAcessada) {this.vezesAcessada = vezesAcessada;}
    public long getInfo() {return info;}
    public void setInfo(long info) {this.info = info;}
    public boolean isIsModified() {return isModified;}
    public void setIsModified(boolean isModified) {this.isModified = isModified; }

    /*@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Pagina) {
            Pagina a = (Pagina) obj;
            if(this.frequencia == a.getOcorrencias()){
                return true;
            }
        }
        return false;
    }*/  
}
