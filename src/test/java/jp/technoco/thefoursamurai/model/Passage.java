package jp.technoco.thefoursamurai.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Passage {

    String id;

    String boke;

    String tsukkomi;

    String boke_name;

    String tsukkomi_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoke() {
        return boke;
    }

    public void setBoke(String boke) {
        this.boke = boke;
    }

    public String getTsukkomi() {
        return tsukkomi;
    }

    public void setTsukkomi(String tsukkomi) {
        this.tsukkomi = tsukkomi;
    }

    public String getBoke_name() {
        return boke_name;
    }

    public void setBoke_name(String boke_name) {
        this.boke_name = boke_name;
    }

    public String getTsukkomi_name() {
        return tsukkomi_name;
    }

    public void setTsukkomi_name(String tsukkomi_name) {
        this.tsukkomi_name = tsukkomi_name;
    }

}
