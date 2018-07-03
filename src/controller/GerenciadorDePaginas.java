package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Pagina;
import util.Arquivos;
import util.MergeSort;

/**
 * Classe responsável por gerar objetos capazes de gerenciar páginas, que são arquivos txt no HD.
 * @author Anésio Sousa
 */
public class GerenciadorDePaginas {
    private MergeSort mergeSort;
    private Arquivos files;
    private ArrayList<Pagina> paginas;
    private ArrayList<Pagina> adicionadas;
    private ArrayList<Pagina> editadas;
    private ArrayList<Pagina> removidas;

    /**
     * Contrutor da classe GerenciadorDePaginas, nele são inicializados os atributos da classe.
     * Cada vez que um gerenciador de páginas é instanciado, a lista de páginas que ele gerencia 
     * já é preenchida. Esse preenchimento é feito pegando todos os arquivos de um determinado diretório
     * e passando para uma lista de Páginas.
     */
    public GerenciadorDePaginas() {
        this.mergeSort = new MergeSort();
        this.files = new Arquivos();
        this.paginas = new ArrayList<>();
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio()); // Necessário pra saber os arquivos add no repo.
        this.adicionadas = new ArrayList<>();
        this.editadas = new ArrayList<>();
        this.removidas = new ArrayList<>();
    }
    /**
     * Método auxiliar que junta os métodos de verificação de integridade em um só.
     */
    private void verificarECorrigirMudancas() { // ACHO QUE ISSO AQUI VAI É PRO CONTROLLER MASTER
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
            File arq = getArquivo(paginas.get(i).getTitulo());
            if (arq == null) {
                flag = true;
                //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO REMOVIDO!!");
                removidas.add(paginas.get(i));
            }
        }
        
        for (int i = 0; i < paginas.size(); i++) {  // Pega os arquivos editados
            File arq = getArquivo(paginas.get(i).getTitulo());
            //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO PRESENTE!!");
            if (paginas.get(i).getInfo() != arq.lastModified()) {
                flag = true;
                editadas.add(paginas.get(i));
            }
        }
        
        /* TEM QUE ADD NOVOS ARQUIVOS À LISTA DE MODIFICADOS.*/
        ArrayList<Pagina> aComparar = new ArrayList();
        aComparar = files.passarArqParaPaginas(aComparar, files.obterRepositorio());

        
        mergeSort.sort(paginas);
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
    
    /**
     *
     * @return
     */
    public boolean arqIsModified() {  // TESTAR ISSO!
        
        //limparMarcadores();
        boolean a = identificarAlteracoes();
        
        // a == true se algo foi modificado
        
        limparMarcadores();
        
        return a;
    }
    
    /**
     *
     * @param titulo
     * @return
     */
    public File getArquivo(String titulo) {
        ArrayList<File> arq = files.obterRepositorio();
        for (File arquivo : arq) {
            if (arquivo.getName().equals(titulo)) {
                return arquivo;
            }
        }
        return null;
    }

    /**
     *
     * @param qtd
     * @return
     */
    public List topKMaisPagina(int qtd){
        mergeSort.sort(paginas);
        List<Pagina> aux = paginas.subList(0, qtd);          
        return aux;
    }
    
    /**
     *
     * @param qtd
     * @return
     */
    public List topKMenosPagina(int qtd){
        Comparator<Pagina> comp = new Comparator<Pagina>(){
            @Override
            public int compare(Pagina o1, Pagina o2) {
                if (o1.getVezesAcessada() > o2.getVezesAcessada()) {
                    return 1;
                }
                if (o1.getVezesAcessada() < o2.getVezesAcessada()) {
                    return -1;
                }
                
                return 0;
            }
        };
        
        mergeSort.sort(paginas, comp);
        List<Pagina> aux = paginas.subList(0, qtd);
        
        return aux;
    }
    
    /**
     *
     * @return
     */
    public boolean paginasTemAcessos(){
        for(Pagina pag: paginas){
            if(pag.getVezesAcessada() != 0){
                return true;
            }
        }
        return false;
    }
    
    /**
     *
     * @return
     */
    public ArrayList getPaginas() {
        return paginas;
    }
}
