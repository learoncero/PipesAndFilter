package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.ArrayList;
import java.util.List;

public class PullSource extends Pull<Face, Face> {

    private final List<Face> faces;

    public PullSource() {
        super(null);
        this.faces = new ArrayList<>();
    }

    public Face pull() {
        return !hasNext() ? null : faces.remove(faces.size() - 1);
    }

    public void setFaces(List<Face> faces) {
        this.faces.addAll(faces);
    }

    @Override
    public boolean hasNext() {
        return !faces.isEmpty();
    }
}