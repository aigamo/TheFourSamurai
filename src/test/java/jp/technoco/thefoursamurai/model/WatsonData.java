package jp.technoco.thefoursamurai.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class WatsonData {

    private List<Adds> addss;

    private Commit commit;

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public List<Adds> getAddss() {
        return addss;
    }

    public void setAddss(List<Adds> addss) {
        this.addss = addss;
    }

}
