package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Pagina;
import util.Arquivos;
import util.MergeSort;

/**
 *
 * @author Anésio Sousa
 */
public class GerenciadorDePaginas {
    private MergeSort mergeSort;
    private Arquivos files;
    private ArrayList<Pagina> paginas;
    private ArrayList<Pagina> adicionadas;
    private ArrayList<Pagina> editadas;
    private ArrayList<Pagina> removidas;

    public GerenciadorDePaginas() {
        this.mergeSort = new MergeSort();
        this.files = new Arquivos();
        this.paginas = new ArrayList<>();
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio()); // Necessário pra saber os arquivos add no repo.
        this.adicionadas = new ArrayList<>();
        this.editadas = new ArrayList<>();
        this.removidas = new ArrayList<>();
    }
    
    private void prePesquisa() { // ACHO QUE ISSO AQUI VAI É PRO CONTROLLER MASTER
        if (identificarAlteracoes()) {
            atualizarPaginas();
            //atualizarArvore(adicionadas, editadas, removidas); // REVER ISSO!!!. 
            limparMarcadores();
        }
    }
    
    private boolean identificarAlteracoes() {  // RETORNA true SE ALGO FOI MODIFICADO
        boolean flag = false;

        // SE PASSAR DIRETO, É PQ NENHUM ARQUIVO FOI REMOVIDO NEM MODIFICADO.
        for (int i = 0; i < paginas.size(); i++) {
            File arq = getPagina(paginas.get(i).getTitulo());
            if (arq == null) {
                flag = true;
                //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO REMOVIDO!!");
                removidas.add(paginas.get(i));
            }
        }
        
        mergeSort.sort(paginas);

        for (int i = 0; i < paginas.size(); i++) {  // Pega os arquivos editados
            File arq = getPagina(paginas.get(i).getTitulo());
            //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO PRESENTE!!");
            if (paginas.get(i).getInfo() != arq.lastModified()) {
                flag = true;
                editadas.add(paginas.get(i));
            }
        }
        
        /* TEM QUE ADD NOVOS ARQUIVOS À LISTA DE MODIFICADOS.*/
        ArrayList<Pagina> aComparar = new ArrayList();
        aComparar = files.passarArqParaPaginas(aComparar, files.obterRepositorio());

        
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
                }
            }
        }

        return flag;
    }
    
    // Remover da lista principal de páginas os arquivos removidos, e marca os modificados e novos.
    private void atualizarPaginas() { // TEM QUE PASSAR AQUI ANTES DE PASSAR NA "atualizarArvore"        
        for (Pagina pag : adicionadas) {  // Adiciona à lista de páginas principal novas páginas encontradas no repositório.
            Pagina p = new Pagina();
            p.setTitulo(pag.getTitulo());
            if (!paginas.contains(p)) {   // é aqui que dá pra identificar as páginas modificadas das novas
                paginas.add(p);
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
        adicionadas.clear();
        removidas.clear();
    }
    
    public boolean arqIsModified() {  // TESTAR ISSO!
        
        //limparMarcadores();
        boolean a = identificarAlteracoes();
        
        // a == true se algo foi modificado
        
        limparMarcadores();
        
        return a;
    }
    
    public File getPagina(String titulo) {
        ArrayList<File> arq = files.obterRepositorio();
        for (File arquivo : arq) {
            if (arquivo.getName().equals(titulo)) {
                return arquivo;
            }
        }
        return null;
    }

    public List topMaisPagina(int qtd){
        mergeSort.sort(paginas);
        return null;
    }
    
    public List topMenosPagina(int qtd){
        
        return null;
    }
    
    public ArrayList getPaginas() {
        return paginas;
    }
}