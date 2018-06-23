package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Anésio
 */
public class Arquivos{

    public ArrayList<File> obter(){
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

}
