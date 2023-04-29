package com.example.location;

import androidx.annotation.Nullable;


import java.util.Comparator;


public class contact {


    public String name;


    public String num;

    Boolean ask;
    public contact(String num,String name, boolean ask){
        this.num=num;
        this.name = name;
        this.ask=false;
    }

    public String getName() {
        return name;
    }
    public String getNum() {
        return num;
    }
    public Boolean getAsk() {return ask;}

    static Comparator<contact> asc = Comparator.comparing(contact::getName);

    @Override
    public boolean equals(@Nullable Object obj) {
        contact t = (contact) obj;
        assert t != null;
        return name.equals(t.name);
    }
}