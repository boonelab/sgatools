package models;

import java.util.Map;

import play.mvc.Http.MultipartFormData;

public class DAjob {

    public String inputHmap;
    public String inputHist;
    public String saveType;

    public DAjob() {
    }
    
    public DAjob(MultipartFormData data) {
        Map<String, String[]> m = data.asFormUrlEncoded();
        inputHmap = m.get("inputHmap")[0];
        inputHist = m.get("inputHist")[0];
        saveType = m.get("saveType")[0];
    }
}
