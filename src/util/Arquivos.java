package util;

import java.io.*;
import java.util.Scanner;
import java.io.FileWriter;

/**
 *
 * @author Anésio
 */
public class Arquivos{

    public File[] obter(){
        File[] arquivos = null;
        try{
            String dirPadrao = new File ("repo").getCanonicalPath();
            File diretorio = new File(dirPadrao);
            arquivos = diretorio.listFiles();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return arquivos;
    }

}
