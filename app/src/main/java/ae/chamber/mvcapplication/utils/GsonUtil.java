package ae.chamber.mvcapplication.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import ae.chamber.mvcapplication.model.ResponseAPI;

public class GsonUtil<T> {

    static GsonUtil gson;

    public static GsonUtil getInstance() {
        if(gson==null)
            gson = new GsonUtil();
        return gson;
    }

    public ResponseAPI gsonToResponseAPI(JSONObject json) {

        ResponseAPI model = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString =  json.toString();
            model = gson.fromJson(jsonString, ResponseAPI.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return model;
    }

}