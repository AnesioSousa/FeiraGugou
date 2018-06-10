package util;

import java.io.*;
import java.util.Scanner;
import java.io.FileWriter;

/**
 *
 * @author Anésio
 */
public class ManipuladorDeArquivos{
    /**
     * Método que faz as leituras dos arquivos de texto de um determinado caminho no sistema.
     * @param local - diretório dos arquivos de texto.
     * @return 
     */
    public File[] leitor(String local){
        File[] arquivos = null;
        try{
            File diretorio = new File (local);
            arquivos = diretorio.listFiles();
            
            for(int i = 0; i < arquivos.length; i++){
                Scanner input = new Scanner(arquivos[i]);
                
                input.close();
            }           
        }catch(FileNotFoundException e){
            System.out.println(e);
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
