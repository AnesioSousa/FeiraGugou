package util;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * 
 * @author Anésio Sousa
 * @param <T> tipo genérico.
 */
public class MergeSort<T extends Comparable<T>> {

    /**
     * Ordena a lista especificada em ordem ascendente, de acordo com a ordem natural dada pelo Comparable desses objetos.
     * Método publico para ordenação. Esse método chama um método método privado responsável por fazer a ordenação.Todos os 
     * elementos da lista devem implementar a interface Comparable;
     * @param a lista a ser ordenada.
     */
    public void sort(ArrayList<T> a) {
        mergeSort(0, a.size() - 1, a, new ArrayList<>(a));
    }

    /**
     * Ordena a lista especificada em ordem ascendente, de acordo com a ordem natural dada por um Comparator.
     * Método publico para ordenação. Esse método chama um método método privado responsável por fazer a ordenação.
     * @param a lista a ser ordenada.
     * @param comp critério a ser utilizado para comparar os objetos.
     */
    public void sort(ArrayList<T> a, Comparator comp) {
        mergeSort(0, a.size() - 1, a, new ArrayList<>(a), comp);
    }
    /**
     * Ordena a lista especificada em ordem ascendente, de acordo com a ordem natural dada pelo Comparable desses objetos.
     * Método privado de ordenação. Para fazer a ordenação da lista recebida, ele "quebra" todos os seus itens até que todos
     * os itens tenham tamanho 1. Depois ele passa para o método merge que irá fazer a junção dessas "mini listas" de tamanho 1
     * comparando uma com a outra (usando a ordem natural do Comparable) e as posicionando no lugar certo. Além desse método 
     * receber a lista a ser ordenada, tambem recebe dados provinientes do seu método publico que serão utilizados para a
     * ordenação dessa lista.
     * @param low valor inicial da lista.
     * @param high valor final (máximo) da lista.
     * @param a lista a ser ordenada.
     * @param aux uma lista auxiliar.
     */
    private void mergeSort(int low, int high, ArrayList<T> a, ArrayList<T> aux) {

        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(low, mid, a, aux);
            mergeSort(mid + 1, high, a, aux);
            merge(low, mid, high, a, aux);
        }
    }
    /**
     * Ordena a lista especificada em ordem ascendente, de acordo com a ordem natural dada por um Comparator.
     * Método privado de ordenação. Para fazer a ordenação da lista recebida, ele "quebra" todos os seus itens até que todos
     * os itens tenham tamanho 1. Depois ele passa para o método merge que irá fazer a junção dessas "mini listas" de tamanho 1
     * comparando uma com a outra (usando a ordem natural dada pelo Comparator) e as posicionando no lugar certo. Além desse método 
     * receber a lista a ser ordenada, tambem recebe dados provinientes do seu método publico que serão utilizados para a
     * ordenação dessa lista.
     * @param low valor inicial da lista.
     * @param high valor final (máximo) da lista.
     * @param a lista a ser ordenada.
     * @param aux uma lista auxiliar.
     * @param comp critério a ser utilizado para comparar os objetos.
     */
    private void mergeSort(int low, int high, ArrayList<T> a, ArrayList<T> aux, Comparator comp) {

        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(low, mid, a, aux, comp);
            mergeSort(mid + 1, high, a, aux, comp);
            merge(low, mid, high, a, aux, comp);
        }
    }
    /**
     * Pega todas as "mini listas" e as junta em uma lista só, comparando seus valores (usando Comparable) e as ordenando.
     * @param low valor inicial da lista.
     * @param mid valor do meio (pegando o low e o high) da lista.
     * @param high valor final (máximo) da lista.
     * @param a lista a ser ordenada.
     * @param aux uma lista auxiliar.
     */
    private void merge(int low, int mid, int high, ArrayList<T> a, ArrayList<T> aux) {

        int left = low;
        int right = mid + 1;

        for (int i = low; i <= high; i++) {
            aux.set(i, a.get(i));
        }

        while (left <= mid && right <= high) {
            a.set(low++, aux.get(left).compareTo(aux.get(right)) < 0 ? aux.get(left++) : aux.get(right++));
        }

        while (left <= mid) {
            a.set(low++, aux.get(left++));
        }
    }
    /**
     * Pega todas as "mini listas" e as junta em uma lista só, comparando seus valores (usando Comparator) e as ordenando.
     * @param low valor inicial da lista.
     * @param mid valor do meio (pegando o low e o high) da lista.
     * @param high valor final (máximo) da lista.
     * @param a lista a ser ordenada.
     * @param aux uma lista auxiliar.
     * @param comp critério a ser utilizado para comparar os objetos.
     */
    private void merge(int low, int mid, int high, ArrayList<T> a, ArrayList<T> aux, Comparator comp) {

        int left = low;
        int right = mid + 1;

        for (int i = low; i <= high; i++) {
            aux.set(i, a.get(i));
        }

        while (left <= mid && right <= high) {
            a.set(low++, comp.compare(aux.get(left), aux.get(right)) < 0 ? aux.get(left++) : aux.get(right++));
        }

        while (left <= mid) {
            a.set(low++, aux.get(left++));
        }
    }
}
