package util;

import java.io.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anésio
 */
public class ManipuladorDeArquivos{
    public static void main(String[] args) {
        leitor();
    }

    public static File[] leitor(){
        File[] arquivos = null;
        try{
            String dirPadrao = new File ("repo").getCanonicalPath();
            File diretorio = new File(dirPadrao);
            arquivos = diretorio.listFiles();
            
            for (File arquivo : arquivos) {
                Scanner input = new Scanner(arquivo);
                input.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ManipuladorDeArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (File arquivo : arquivos) {
            System.out.println(arquivo.getName());
        }
        
        
        return arquivos;
    }
    /*
    public void leitor(String local){
        try{
            BufferedReader leituraBuff = new BufferedReader(new FileReader(local));
            String linha = "";

            while(true){
                if(linha != null)
                    System.out.println(linha);
                else
                    break;
                linha = leituraBuff.readLine();
            }
            leituraBuff.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    public void escritor(String caminho){
         try{
            BufferedWriter escrita = new BufferedWriter(new FileWriter(caminho));
            String linha = "";
            Scanner in = new Scanner(System.in);
            System.out.println("Escreva algo: ");
            linha = in.nextLine();
            escrita.append(linha + "\n");
            escrita.close();
         }catch(IOException e){
             System.out.println(e);
         }
    }
    /*
    public void lerComBuffered(){
       try{
        FileWriter arq = new FileWriter("Test.txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        
        int n = 5;
        
        gravarArq.printf("+--Resultado--+%n");
        for (int i = 0; i < 10; i++) {
            gravarArq.printf("| %2d X %d = %2d |%n", i, n, (i*n));
        }
        gravarArq.printf("+-------------+%n");
        arq.close();
        
       }catch(IOException e){   
       }
    }*/
}
