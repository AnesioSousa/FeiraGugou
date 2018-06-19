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
    private ArrayList<Pagina> paginas; // Ver se não dá pra fazer um array de files (arquivos txt);
    
    public Palavra(String palavra){
        palavraChave = palavra;
        paginas = new ArrayList<>();
        vezesBuscada = 0;
    }
    

    public String getPalavraChave() { return palavraChave;}
    
    public void setPalavraChave(String palavraChave) { this.palavraChave = palavraChave;}
    
    public void adicionarPagina(String titulo, int vezes){
        Dados data = new Dados(); // Antiga classe interna "Dados" fazia o mesmo do que a atual pagina. Refazer a logica do sistema.
        data.setTitulo(titulo);
        data.setOcorrencia(vezes);
        paginas.add(data);
    }
    /*public void removerPagina(String titulo){
        Dados data = new Dados();
        
        dadosPaginas.remove(pag);
    }*/
    
    public Iterator iteradorDados(){
        return paginas.iterator();
    }
    @Override
    public String toString() {
        return palavraChave;
    }
    
}