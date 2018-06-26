package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import model.Pagina;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Teste da classe Arquivos.
 * @author Anésio Sousa
 */
public class ArquivosTest {
    /**
     * Teste do método obterRepositorio, da classe Arquivos.
     */
    @Test
    public void testObterRepositorio() {   // Esse teste só passa se o repositório existir e tiver itens.
        try {
        
        Arquivos arquivos = new Arquivos();
        String dirPadrao = new File ("repo").getCanonicalPath();
        File diretorio = new File(dirPadrao); 
        assertTrue(diretorio.exists());
        ArrayList<File> resultado = arquivos.obterRepositorio();
        assertNotNull(resultado);
        } catch (IOException ex) {
        }
    }

    /**
     * Teste do método passarArqParaPaginas, da classe Arquivos.
     */
    @Test
    public void testPassarArqParaPaginas() {
        try {
            Arquivos arquivos = new Arquivos();
            ArrayList<Pagina> lista = new ArrayList<>();
            ArrayList<File> arq = new ArrayList<>();
            
            File[] archives;
            String caminho = new File("").getCanonicalPath();
            File dir = new File(caminho);
            archives = dir.listFiles();
            arq.addAll(Arrays.asList(archives));
            
            lista = arquivos.passarArqParaPaginas(lista, arq);
            
            assertFalse(lista.isEmpty());
        } catch (IOException ex) {
        }
        
    }
    
}
