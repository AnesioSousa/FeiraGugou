package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Anésio
 */
public class Palavra {
    private String palavraChave;
    private int vezesBuscada;
    private ArrayList<Dados> dadosPaginas; // Ver se não dá pra fazer um array de files (arquivos txt);
    
    public Palavra(String palavra){
        palavraChave = palavra;
        dadosPaginas = new ArrayList<>();
        vezesBuscada = 0;
    }
    
    private class Dados{
        private String titulo;
        private int ocorrencia;

        private String getTitulo() {return titulo;}
        private void setTitulo(String titulo) {this.titulo = titulo;}
        private int getOcorrencia() {return ocorrencia;}
        private void setOcorrencia(int ocorrencia) {this.ocorrencia = ocorrencia;}  
    }

    public String getPalavraChave() { return palavraChave;}
    
    public void setPalavraChave(String palavraChave) { this.palavraChave = palavraChave;}
    
    public void adicionarPagina(String titulo, int vezes){
        Dados data = new Dados();
        data.setTitulo(titulo);
        data.setOcorrencia(vezes);
        dadosPaginas.add(data);
    }
    /*public void removerPagina(String titulo){
        Dados data = new Dados();
        
        dadosPaginas.remove(pag);
    }*/
    
    public Iterator iteradorDados(){
        return dadosPaginas.iterator();
    }
    @Override
    public String toString() {
        return palavraChave;
    }
    
}