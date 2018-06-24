package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import model.Pagina;

/**
 *
 * @author Anésio
 */
public class Arquivos{

    public ArrayList<File> obterRepositorio(){
        ArrayList<File> arq = new ArrayList<>();
        try{
            File[] arquivos;
            String dirPadrao = new File ("repo").getCanonicalPath();
            File diretorio = new File(dirPadrao); 
            arquivos = diretorio.listFiles();
            arq.addAll(Arrays.asList(arquivos));
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return arq;
    }
    
    public ArrayList<Pagina> passarArqParaPaginas(ArrayList<Pagina> lista, ArrayList<File> arq){
        for (File a : arq) {
            Pagina pagina = new Pagina();
            pagina.setTitulo(a.getName());
            pagina.setInfo(a.lastModified());
            lista.add(pagina);
        }
        
        return lista;
    }

}
