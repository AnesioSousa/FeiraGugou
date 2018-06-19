package model;

import java.util.ArrayList;

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
    
    public void adicionarPagina(Pagina pag){
        paginas.add(pag);
    }
    public void removerPagina(Pagina pag){
        paginas.remove(pag);
    }
    
}
