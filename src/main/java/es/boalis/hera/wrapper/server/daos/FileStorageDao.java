package es.boalis.hera.wrapper.server.daos;

import es.boalis.hera.wrapper.server.BeanMessage;
import es.boalis.hera.wrapper.server.StorageDao;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.logging.Logger;

public class FileStorageDao implements StorageDao {
    private String fileName;
    private int countToFlush =10;
    private final Logger log = Logger.getLogger(FileStorageDao.class.getName());
    FileOutputStream fos = null;
    private Stack<String> stack = new Stack<>();
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setCountToFlush(int countToFlush) {
        this.countToFlush = countToFlush;
    }

    public void start()throws IOException {
        fos = new FileOutputStream(fileName,true);
    }
    @Override
    public void insert(BeanMessage message) throws Exception {
        this.insertP(message);
        var size = stack.size();
        if (size == countToFlush){
            //log.info("firing data");
            this.fire();
        }
    }
    private void insertP(BeanMessage mesg){
        var stringy = new JSONObject(mesg);
        stack.push(stringy.toString());
        //log.info(stringy.toString());
        log.fine("buffer size: "+stack.size());
    }
    public synchronized void fire()throws IOException{
        log.info("flushing data");
        while(!stack.empty()){
            var line = stack.pop();
            log.info("line: "+line);
            fos.write(line.getBytes());
            fos.write("\r\n".getBytes());
        }
        fos.flush();
    }
    public void close()throws IOException{
        this.fire();
        this.fos.close();
    }
}
