package es.boalis.hera.wrapper.server.daos;

import es.boalis.hera.wrapper.server.BeanMessage;
import es.boalis.hera.wrapper.server.StorageDao;

import java.io.IOException;
import java.util.logging.Logger;

public class RollingFileStorageDao implements StorageDao {
    Logger log = Logger.getLogger(RollingFileStorageDao.class.getName());
    private int maxRegisters=10000;
    private int currentEntries=0;
    private int fileCounter=0;
    private int countToFlush =5;
    private String filename;

    private String path;

    private FileStorageDao writter;

    public int getMaxRegisters() {
        return maxRegisters;
    }

    public void setMaxRegisters(int maxRegisters) {
        this.maxRegisters = maxRegisters;
    }

    public int getFileCounter() {
        return fileCounter;
    }

    public void setFileCounter(int fileCounter) {
        this.fileCounter = fileCounter;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getCountToFlush() {
        return countToFlush;
    }

    public void setCountToFlush(int countToFlush) {
        this.countToFlush = countToFlush;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void start()throws IOException{
        writter = new FileStorageDao();
        writter.setCountToFlush(this.getCountToFlush());
        var filename = this.getPath()+this.getFilename()+"_"+this.getFileCounter()+".json";
        log.info("path: " +this.getPath());
        log.info("file: " +this.getFilename());
        log.info("file url: " +filename);
        writter.setFileName(filename);
        writter.start();
    }


    @Override
    public void insert(BeanMessage message) throws Exception {
        currentEntries++;
        writter.insert(message);
        if (currentEntries>=this.getMaxRegisters()){
            this.fileCounter++;
            this.rotateFile();
            this.currentEntries=0;
        }
    }

    protected synchronized void rotateFile()throws IOException{
        writter.close();
        this.start();
    }
}
