package util;

import java.util.ArrayList;
import java.util.Comparator;

public class MergeSort<Pagina extends Comparable<Pagina>> {

    public void sort(ArrayList<Pagina> a) {
        mergeSort(0, a.size() - 1, a, new ArrayList<>(a));
    }

    public void sort(ArrayList<Pagina> a, Comparator comp) {
        mergeSort(0, a.size() - 1, a, new ArrayList<>(a), comp);
    }

    private void mergeSort(int low, int high, ArrayList<Pagina> a, ArrayList<Pagina> aux) {

        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(low, mid, a, aux);
            mergeSort(mid + 1, high, a, aux);
            merge(low, mid, high, a, aux);
        }
    }

    private void mergeSort(int low, int high, ArrayList<Pagina> a, ArrayList<Pagina> aux, Comparator comp) {

        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(low, mid, a, aux, comp);
            mergeSort(mid + 1, high, a, aux, comp);
            merge(low, mid, high, a, aux, comp);
        }
    }

    private void merge(int low, int mid, int high, ArrayList<Pagina> a, ArrayList<Pagina> aux) {

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

    private void merge(int low, int mid, int high, ArrayList<Pagina> a, ArrayList<Pagina> aux, Comparator comp) {

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
