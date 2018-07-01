package view;

//import controller.Controlador;
import controller.Controlador;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import util.ArvoreAVL;
import java.util.Scanner;
import model.Dados;
import model.Pagina;
import util.*;

/**
 *
 * @author Anésio
 */
class Testes {
    public static void main(String[] args) {
        //ArvoreAVL tree = new ArvoreAVL();
        Controlador ctr = new Controlador();
       // Arquivos man = new Arquivos();
        Scanner input = new Scanner(System.in);
        String op = null;
        
        ArrayList<Pagina> paginas = new ArrayList<>();
        
        while( !"sair".equals(op)){
            System.out.printf("Digite a palavra: ");
            String palavra = input.nextLine();
            
            ArrayList ret = ctr.pesquisar(palavra);
            if(ret == null){
                System.out.println("Não foi achado!");
            }else{
                for(Object d: ret){
                    System.out.println(d);
                }
            }
     
            System.out.println("Deseja sair?");
            op = input.nextLine();
            /*System.out.println("(1) - Para exibir resultados em ordem crescente");
            System.out.println("(2) - Para exibir resultados em ordem decrescente");
            int i = input.nextInt();
            switch(i){
                case 1: ctr.listarResultadosEmOrdemCrescente(); break;
                case 2: ctr.listarDecrescente(); break;
            }*/
        }
    }
}    


        //ArrayList<File> arquivos = man.obter();
        //ArrayList<Pagina> paginas = new ArrayList<>();
       // MergeSort m = new MergeSort();
        
        /*Pagina a = new Pagina();
        a.setTitulo("Anésio");
        a.setOcorrencias(5);
        Pagina b = new Pagina();
        b.setTitulo("Beatriz");
        b.setOcorrencias(7);
        Pagina c = new Pagina();
        c.setTitulo("Carlos");
        c.setOcorrencias(9);
        Pagina d = new Pagina();
        d.setTitulo("Daniel");
        d.setOcorrencias(2);
        Pagina e = new Pagina();
        e.setTitulo("Emanuel");
        e.setOcorrencias(6);
        
        paginas.add(a);
        paginas.add(d);
        paginas.add(c);
        paginas.add(b);
        paginas.add(e);*/
        
        /*Pagina[] pag = new Pagina[paginas.size()];
        
        paginas.toArray(pag);*/
        
        //System.out.println(a.compareTo(e));
        
        /*Comparator<Pagina> com = new Comparator<Pagina>(){
            @Override
            public int compare(Pagina o1, Pagina o2) {
                if(o1.getOcorrencias() > o2.getOcorrencias()){
                    return 1;
                }
                if(o1.getOcorrencias() < o2.getOcorrencias()){
                    return -1;
                }
                return 0;
            }   
        };*/
        
        /*Comparator<Pagina> comp = new Comparator<Pagina>(){                 // Cria um comparador por informação de modificação;
            @Override
            public int compare(Pagina o1, Pagina o2) {
                return o1.getTitulo().compareTo(o2.getTitulo());
            }
        };
        
        m.sort(paginas, comp);
        for(Pagina p: paginas){
            System.out.println(p);
        }*/
//===============================================================================================================================\\
    /*Iterator<Pagina> itr = ctr.listarPaginas();
        
        while(itr.hasNext()){
            Pagina a = itr.next();
            System.out.println(a);

        }*/
        
        
        /*Iterator<Long> itr = ctr.getAlteracoes();
        
        while(itr.hasNext()){
            long a = itr.next();
            
            System.out.println(a);
        }*/
        /*input.nextLine();
        
        System.out.println(osArquivosEstaoIntegros(arquivos));*/
//=================================================================================================================================\\
    
     /*private static boolean osArquivosEstaoIntegros(File[] arquivos) {
        Arquivos man = new Arquivos();
        File[] aComparar = man.obter();
        long[] trab = null;
        long q;
        
        for(int i =0; i< aComparar.length; i++) {
            q = aComparar[i].lastModified();
            trab[i] = q;
        }
        
        
        for(int i =0; i< aComparar.length; i++) {
            q = aComparar[i].lastModified();
            trab[i] = q;
        }
        
        
        
        
        return Arrays.equals(auxiliar, aComparar);
    }*/
