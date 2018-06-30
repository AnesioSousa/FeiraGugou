package model;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class VerificadorDeDiretorio extends Thread { // SE FUNCIONAR, MOVER PARA UTIL
    public static void main(String[] args) {
        /*WatchService watchService = FileSystems.getDefault().newWatchService();
        
        Path directory = Paths.get("repo");
        
        WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        
        while(true){
            for(WatchEvent<?> event : watchKey.pollEvents()){
                System.out.println(event.kind());
            }
            
        }*/
        
        
        
        //1- Passo: criar uma instância de serviço de monitoramento usando java.nio.file.FileSystems:
        try (WatchService watchService = FileSystems.getDefault().newWatchService()){ // aqui identifica o tipo do sistema.
            //Map<WatchKey, Path> keyMap = new HashMap<>();
        //2- Passo: criar um caminho para o diretório que queremos monitorar:
            Path path = Paths.get("repo");
            /*keyMap.put(path.register(service, 
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY)
                    , path);*/
            
            
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
            
            //do {                
                //watchKey = watchService.take();
                //Path eventDir = keyMap.get(watchKey);
            while((watchKey = watchService.take()) != null){
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    
                    //Path eventPath = (Path)event.context();
                    Thread.sleep( 50 );
                    
                    System.out.println(/*eventDir + ": " + */kind + ": " + event.context() + ".");
                }
                watchKey.reset();
            }  
            //} while (watchKey.reset());
        }catch(Exception e){
            // TODO: handle exception
        }
        
    }
    
    /*public void go(){
        start();
    }
    
    @Override
    @SuppressWarnings( "SleepWhileInLoop" )
    public void run(){
        boolean listening = true;
        while(true){
            try(WatchService watchService = FileSystems.getDefault().newWatchService()){
                
                WatchKey key = watchService.take();
                Path path = Paths.get("repo");
                
                Thread.sleep(50);
                
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changed = path.resolve((Path)event.context());
                    
                    if( event.kind() == StandardWatchEventKinds.ENTRY_MODIFY && !listening){
                        System.out.println("Changed: " + changed);
                    }
                }
                
                if(!key.reset()){
                    
                }
                
            }catch( IOException | InterruptedException ex ) {
                listening = false;
            }
        }
    }*/

}