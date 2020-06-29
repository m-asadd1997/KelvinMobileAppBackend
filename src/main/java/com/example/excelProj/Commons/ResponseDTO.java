package com.example.excelProj.Commons;

import java.util.List;

public class ResponseDTO<T> {

    private List<T> list;
    private T object;


    public ResponseDTO(List<T> list, T object) {
        this.list = list;
        this.object = object;
    }

    public ResponseDTO() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
