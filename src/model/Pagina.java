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
    private boolean isModified;
    private int ocorrencias;

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public int getVezesAcessada() {return vezesAcessada;}
    public void setVezesAcessada(int vezesAcessada) {this.vezesAcessada = vezesAcessada;}
    public int getOcorrencias() {return ocorrencias;}
    public void setOcorrencias(int ocorrencias) {this.ocorrencias = ocorrencias;}
    public long getInfo() {return info;}
    public void setInfo(long info) {this.info = info;}
    public boolean isIsModified() {return isModified;}
    public void setIsModified(boolean isModified) {this.isModified = isModified; }
    
    

    @Override
    public String toString() {
        //int pos = titulo.indexOf(".");
        //String nome = titulo.substring(0, pos); 
        return "Pagina{" + "titulo=" + titulo /*nome*/ + ", ocorrencias=" + ocorrencias + '}';
        //                                         PARA TESTES!!! RETIRAR DEPOIS
    }

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
            if(this.ocorrencias == a.getOcorrencias()){
                return true;
            }
        }
        return false;
    }*/
    
    @Override
    public int compareTo(Pagina o){
        if(this.getOcorrencias() > o.getOcorrencias()){
            return- 1;
        }
        if(this.getOcorrencias() < o.getOcorrencias()){
            return- -1;
        }
        return 0;/*this.titulo.compareTo(o.titulo);*/ // Só de exemplo. A ordem natural de paǵinas deve ser por relevancia, ou seja, por ocorrencia da palavra
    }
    
}
