package quru.qa.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Student {

    public String name;
    public String surname;
    @JsonProperty("favorite_music")
    public ArrayList<String> favoriteMusic;
    public Address address;

}
