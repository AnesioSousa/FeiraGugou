package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import model.*;
import util.ManipuladorDeArquivos;

/**
 *
 * @author Anésio
 */
public class Controlador {
    private ArrayList<Pagina> paginas = new ArrayList<>();
    private ManipuladorDeArquivos man = new ManipuladorDeArquivos();
    
    public void pesquisar(String palavra){
        
    }
    
    
    private void atribuirPaginas(){
        File[] arquivos = man.pegarArquivosNoDir();
        for (int i = 0; i < arquivos.length; i++) {
            Pagina pag = new Pagina();
            int pos = arquivos[i].getName().indexOf(".");
            pag.setTitulo(arquivos[i].getName().substring(0, pos)); 
            paginas.add(pag);
        }
        
        for (Pagina p : paginas) {
            System.out.println(p);
        }
    }
    
    public Iterator<Pagina> getPaginas() {
        return paginas.iterator();
    }
  
}
