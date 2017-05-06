package com.example.gold.memowithnodejs;

import com.example.gold.memowithnodejs.domain.Data;
import com.example.gold.memowithnodejs.domain.Qna;

import java.util.List;

/**
 * Created by Gold on 2017. 3. 24..
 */

public class DataStore {
    private static DataStore instance = null;
    private DataStore(){}

    public static DataStore getInstance(){
        if(instance == null){
            instance = new DataStore();
        }
        return instance;
    }

    private List<Qna> datas;

    public List<Qna> getDatas() {
        return datas;
    }

    public void setDatas(List<Qna> datas) {
        this.datas = datas;
    }
}
