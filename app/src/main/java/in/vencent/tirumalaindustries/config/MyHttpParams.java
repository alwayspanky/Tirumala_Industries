package in.vencent.tirumalaindustries.config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MyHttpParams {

    private static List<NameValuePair> params = new ArrayList<NameValuePair>();

    public List<NameValuePair> getParams(){
        return params;
    }

    public void addParam(String field, String value){
        params.add(new BasicNameValuePair(field, value));
    }

    public String getParamsAsUrl(){
        String paramsUrl = "";
        NameValuePair field;
        int size = params.size();
        for(int i = 0; i < size; i++){
            field = (NameValuePair) params.get(i);
            paramsUrl += field.getName() + "=" + field.getValue();
            if(i != (size-1)){
                paramsUrl += "&";
            }
        }
        return paramsUrl;
    }
}
