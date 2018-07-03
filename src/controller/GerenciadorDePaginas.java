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
    private GerenciadorDeArvores controlTree;

    /**
     * Contrutor da classe GerenciadorDePaginas, nele são inicializados os atributos da classe. Cada vez que um gerenciador de páginas é instanciado, a lista de páginas que ele gerencia já é preenchida. Esse preenchimento é feito pegando todos os arquivos de um determinado diretório e passando para uma lista de Páginas.
     */
    public GerenciadorDePaginas() {
        this.mergeSort = new MergeSort();
        this.files = new Arquivos();
        this.paginas = new ArrayList<>();
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio()); // Necessário pra saber os arquivos add no repo.
        this.adicionadas = new ArrayList<>();
        this.editadas = new ArrayList<>();
        this.removidas = new ArrayList<>();
        this.controlTree = new GerenciadorDeArvores();
    }

    /**
     * Verifica se o estado da lista de páginas foi modificado. Esse método percorre a lista de páginas atual, procurando por páginas que foram removidas, alteradas e adicionadas ao repositório. Se achar, as separa em listas.
     *
     * @return true se algo foi modificado, false caso contrário.
     */
    public boolean identificarAlteracoes() {
        boolean flag = false;
        for (int i = 0; i < paginas.size(); i++) {
            File arq = getArquivo(paginas.get(i).getTitulo());
            if (arq == null) {
                flag = true;
                removidas.add(paginas.get(i));
            }
        }
        for (int i = 0; i < paginas.size(); i++) {
            File arq = getArquivo(paginas.get(i).getTitulo());
            if (paginas.get(i).getInfo() != arq.lastModified()) {
                flag = true;
                editadas.add(paginas.get(i));
            }
        }
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
    /**
     * Atualiza a lista de páginas principal pegando as páginas removidas e adicionadas no repositório.
     */
    public void atualizarPaginas() {        
        for (Pagina pag : adicionadas) {  // Adiciona à lista de páginas principal novas páginas encontradas no repositório.
            Pagina p = new Pagina();
            p.setTitulo(pag.getTitulo());
            if (!paginas.contains(p)) {   // é aqui que dá pra identificar as páginas modificadas das novas
                paginas.add(p);
            }
        }
        for (Pagina pag : removidas) {    // Remove da lista de páginas principal as páginas retiradas do repositório.
            Pagina p = new Pagina();
            p.setTitulo(pag.getTitulo());
            paginas.remove(p);            
        }
    }

    /**
     * Remove todos os itens das listas marcadores.
     * Se forem identificadas mudanças no repositório, as listas que marcam as páginas alteradas
     * ficarão com os itens marcados. Feita a atualização, elas precisam ser limpas para a próxima 
     * verficação. Esse método faz isso.
     */
    public void limparMarcadores() {
        editadas.clear();
        adicionadas.clear();
        removidas.clear();
    }

    /**
     * Dado um título, retorna um File txt do repositório que contém esse título.
     * @param titulo nome do arquivo a ser procurado.
     * @return o arquivo se ele for encontrado, e null caso contrário.
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
     * Retorna as N paginas mais acessadas.
     * @param qtd quantidade de paginas a ser exibida.
     * @return sublista da lista de paginas principal contendo as N paginas.
     */
    public List topKMaisPagina(int qtd) {
        mergeSort.sort(paginas);
        List<Pagina> aux = paginas.subList(0, qtd);
        return aux;
    }

    /**
     * Retorna as N paginas menos acessadas.
     * @param qtd quantidade de paginas a ser exibida.
     * @return sublista da lista de paginas principal contendo as N paginas.
     */
    public List topKMenosPagina(int qtd) {
        Comparator<Pagina> comp = new Comparator<Pagina>() {
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
     * Retorna a informação de se pelo menos uma tem acessos ou não.
     * @return true se alguma tiver, false caso contrário.
     */
    public boolean paginasTemAcessos() {
        for (Pagina pag : paginas) {
            if (pag.getVezesAcessada() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna a lista de páginas principal.
     * @return lista de páginas.
     */
    public ArrayList getPaginas() {
        return paginas;
    }

    /**
     * Retorna a lista de páginas marcadora de páginas adicionadas.
     * @return lista de páginas.
     */
    public ArrayList getAdicionadas() {
        return adicionadas;
    }

    /**
     * Retorna a lista de páginas marcadora de páginas editadas.
     * @return lista de páginas.
     */
    public ArrayList getEditadas() {
        return editadas;
    }

    /**
     * Retorna a lista de páginas marcadora de páginas removidas.
     * @return lista de páginas.
     */
    public ArrayList getRemovidas() {
        return removidas;
    }

    /**
     * Retorna o controlador de árvores.
     * @return controlador de árvores.
     */
    public GerenciadorDeArvores getControlTree() {
        return controlTree;
    }

}
