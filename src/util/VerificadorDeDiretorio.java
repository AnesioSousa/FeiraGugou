package util;

import controller.GerenciadorDePaginas;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import model.Pagina;

class Testes{
    public static void main(String[] args) {
        Runnable c = new VerificadorDeDiretorio();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(c, 0, 2, TimeUnit.SECONDS);
    }
}

class VerificadorDeDiretorio implements Runnable{ 
    
    private MergeSort mergeSort = new MergeSort();
    private static ArrayList<Pagina> paginas = new ArrayList<>();
    private ArrayList<Pagina> adicionadas = new ArrayList<>();
    private ArrayList<Pagina> removidas = new ArrayList<>();
    private ArrayList<Pagina> editadas = new ArrayList<>();
    private Arquivos files = new Arquivos();
    private boolean flag;
    GerenciadorDePaginas controlPages = new GerenciadorDePaginas();

    public VerificadorDeDiretorio() {
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio()); // Necessário pra saber os arquivos add no repo.
    }
    
    @Override
    public void run() {
       
        System.out.println("a");
        System.out.println(adicionadas.size());
        
        ArrayList<Pagina> aComparar = new ArrayList();
        aComparar = files.passarArqParaPaginas(aComparar, files.obterRepositorio());
        for (int i = 0; i < paginas.size(); i++) {
            boolean match = false;
            for (Pagina p: aComparar) {
                if (paginas.get(i).getTitulo().equals(p.getTitulo())) {
                    match = true;
                    if(paginas.get(i).getInfo() < p.getInfo()){
                        System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO EDITADO!!"); // VAI SEMPRE REPETIR ISSO POR QUE QUEM TEM QUE LIMPAR ISSO É A ÁRVORE,
                        editadas.add(paginas.get(i));
                    }
                }
            }
            if(!match){
                System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO REMOVIDO!!");
                removidas.add(paginas.get(i));
            }
        }
        /*
        Comparator<Pagina> comp = new Comparator<Pagina>(){
            @Override
            public int compare(Pagina o1, Pagina o2) {
                return o1.getTitulo().compareTo(o2.getTitulo());
            }
 
        };
        
        mergeSort.sort(paginas, comp); // VER SE NÃO PRECISA FAZER UM COMPARATOR DE NOME (PODE SER QUE DE BOSTA POR ESTAR ORDENANDO POR ACESSOS)
        
        for (int i = 0; i < paginas.size(); i++) {  // Pega os arquivos editados
            File arq = controlPages.getArquivo(paginas.get(i).getTitulo());
            //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO PRESENTE!!");
            if (paginas.get(i).getInfo() == arq.lastModified()) { // SE NÃO FUNCIONAR, PEGA UM ELEM DE PÁGINAS, E COMPARA COM TODO MUNDO
                
                
                System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO EDITADO!!"); // VAI SEMPRE REPETIR ISSO POR QUE QUEM TEM QUE LIMPAR ISSO É A ÁRVORE,
                editadas.add(paginas.get(i));
            }
        }
        
        mergeSort.sort(aComparar);

        if (paginas.size() < aComparar.size()) {
            for (int i = 0; i < aComparar.size(); i++) {
                Pagina p = new Pagina();
                p.setTitulo(aComparar.get(i).getTitulo());
                if (!paginas.contains(p)) {
                    flag = true;
                    p.setTitulo(aComparar.get(i).getTitulo());
                    p.setInfo(aComparar.get(i).getInfo());
                    adicionadas.add(p);
                    System.out.println(p.getTitulo() + " " + "ARQUIVO ADICIONADO!!");
                }
            }
        }*/
        atualizarPaginas();
        limparMarcadores();
    }
    
    private void atualizarPaginas() { // TEM QUE PASSAR AQUI ANTES DE PASSAR NA "atualizarArvore" 
        for (Pagina pag : adicionadas) {  // Adiciona à lista de páginas principal novas páginas encontradas no repositório.
            Pagina p = new Pagina();
            p.setTitulo(pag.getTitulo());
            if (!paginas.contains(p)) {   // é aqui que dá pra identificar as páginas modificadas das novas
                paginas.add(p);
            }
        }
        
        for(Pagina pag: editadas){
            Pagina p = new Pagina();
            p.setTitulo(pag.getTitulo());
            int aux = paginas.indexOf(p);
            if(aux != -1){
                paginas.get(aux).setInfo(pag.getInfo()); // Ver como fica questão das editadas no método atualizarArvore();
            }
        }
        
        for (Pagina pag : removidas) {
            Pagina p = new Pagina();
            p.setTitulo(pag.getTitulo());
            paginas.remove(p);
        }
    }
    
    private void limparMarcadores() {
        editadas.clear();
        //adicionadas.clear();
        removidas.clear();
    }
}
