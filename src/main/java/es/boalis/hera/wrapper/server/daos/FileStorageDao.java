package es.boalis.hera.wrapper.server.daos;

import es.boalis.hera.wrapper.server.BeanMessage;
import es.boalis.hera.wrapper.server.StorageDao;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public class FileStorageDao implements StorageDao {
    private String fileName;
    private int count=10;

    FileOutputStream fos = null;
    private Stack<String> stack = new Stack<>();
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public void start()throws IOException {
        fos = new FileOutputStream(fileName,true);
    }
    @Override
    public void insert(BeanMessage message) throws Exception {
        this.insertP(message);
        var size = stack.size();
        if (size>=count){
            this.fire();
        }


    }
    private void insertP(BeanMessage mesg){
        var stringy = new JSONObject(mesg);

        stack.push(stringy.toString());
    }
    private synchronized void fire()throws IOException{
        for (int a=0 ;a<stack.size();a++){
            var line = stack.pop();
            fos.write(line.getBytes());
            fos.write("\r\n".getBytes());
        }
        fos.flush();
    }
}
