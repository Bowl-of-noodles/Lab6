package edu.rogachova.server.managers;


import edu.rogachova.common.exceptions.NoAccessException;
import edu.rogachova.common.model.Worker;
import edu.rogachova.common.xml.Workers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class FileManager
{
    private final String path;
    //private final String writefilePath = "C:/Users/Dasha/Desktop/Projects/Lab6/result.xml";
    private final String writefilePath = "/home/s316988/Lab6/result.xml";
    public FileManager(String path){
        this.path = path;
    }


    /**
    * Записывает в файл коллекцию
     */
    public void writeToFile(HashMap<Long, Worker> collection){
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(Workers.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Workers result = new Workers();
            result.setWorkers(collection);
            BufferedOutputStream o = getBufferedOutputStream();
            marshaller.marshal(result, o);
            o.close();

        }catch(JAXBException e){
            System.out.println("Ошибка сохранения в файл");

        }catch(FileNotFoundException e){
            System.out.println("Файл не найден");;
        }catch(IOException e){
            System.out.println("Ошибка сохранения в файл");

        }catch(NoAccessException e){
            System.out.println("Нет доступа к файлу");
        }
    }


    private BufferedOutputStream getBufferedOutputStream() throws IOException, NoAccessException
    {
        File file = new File(writefilePath);
        if(file.exists() && !file.canWrite()){
            throw new NoAccessException();
        }
        return new BufferedOutputStream(new FileOutputStream(new File(writefilePath)));
    }

    public HashMap<Long, Worker> readFile(){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Workers.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.unmarshal(getBufferedInputStream());
            Workers workers = (Workers) unmarshaller.unmarshal(new File(path));
            HashMap<Long, Worker> collection = workers.getWorkers();
            System.out.println("Данные файла загружены");
            return collection;

        }catch(NoSuchElementException e){
            System.out.println("Загрузочный файл пуст");
        }catch(NullPointerException e){
            System.out.println("В читаемой файле не найдена запрашиваемая коллекция");
        }catch(JAXBException e){
            System.out.println("Ошибка прочтения файла");;
        }catch(FileNotFoundException e){
            System.out.println("Файл не найден");;
        }catch(NoAccessException e){
            System.out.println("Нет доступа к файлу");;
        }catch (IOException e){
            System.out.println("Непредвиденная ошибка: "+ e.getMessage());
        }
        return new HashMap<Long, Worker>();
    }

    private BufferedInputStream getBufferedInputStream() throws IOException, NoAccessException
    {
        File file = new File(path);
        if(file.exists() && !file.canWrite()) throw new NoAccessException();
        return new BufferedInputStream(new FileInputStream(path));
    }
}
