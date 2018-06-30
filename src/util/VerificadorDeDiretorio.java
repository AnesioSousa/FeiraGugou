package util;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class VerificadorDeDiretorio{ // SE FUNCIONAR, MOVER PARA UTIL

    public static void main(String[] args) { // tirar de main.

        //1- Passo: criar uma instância de serviço de monitoramento usando java.nio.file.FileSystems:
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) { // aqui identifica o tipo do sistema.
            //2- Passo: criar um caminho para o diretório que queremos monitorar:
            Path path = Paths.get("repo");

            /*3- Passo: registrar o caminho com o serviço de monitoramento.
          Existem dois conceitos importantes para entender nesse estágio:
            A classe StandardWatchEventKinds. 
            E a WatchKey (chave de monitoramento).
             */
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            WatchKey watchKey;

            do {
                watchKey = watchService.take();
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> tipo = event.kind();
                    
                    switch(tipo.name()){
                        case "ENTRY_CREATE": 
                            System.out.println("AAAAAAAAAAAAA");
                            break;
                        case "ENTRY_MODIFY":   
                            System.out.println("BBBBBBBBBBBBB");
                            break;
                        case "ENTRY_DELETE":   
                            System.out.println("CCCCCCCCCCCCC");
                            break;
                            
                    }
                    
                    System.out.println(tipo + ": " + event.context() + ".");
                }
            } while (watchKey.reset());
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
