package es.boalis.hera.wrapper.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class Parser{

    private BufferedReader reader = null;
    public Parser(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }
    private Map<String,String> parseTags(String tagline){
       var tags = tagline.split(";");
       var hTags = new HashMap<String,String>();
       for (String tag:tags){
           var tokens= tag.split("=");
           var key = tokens[0];
           var value = tokens[1];
           hTags.put(key,value);
       }
       return hTags;
    }
    public BeanMessage parseInput()throws Exception{
        var line=reader.readLine();
        if (line == null){
            return null;
        }
        var params = line.split(" ");
        if (params.length!=3){
            return null;
        }
        var metric="";
        var map = (Map)new HashMap<String,String>();
        var tmpValue = params[0];
        var startTag = tmpValue.indexOf(";");
        if (startTag==-1) {
            metric = params[0];
        }else{
            metric = params[0].substring(0,startTag);
            map = this.parseTags(params[0].substring(startTag+1));
        }

        var value = params[1];
        var timestamp = params[2];
        Long timeStampLong = null;
        if (timestamp.equals("-1")){
            timeStampLong = System.currentTimeMillis();
        }else{
            timeStampLong = Long.parseLong(timestamp);
        }
        var floatValue = new Float(value);
        var beanMessage = new BeanMessage(metric,floatValue,timeStampLong);
        beanMessage.setTags(map);
        //System.out.println(beanMessage.toString());
        return beanMessage;
    }

}
