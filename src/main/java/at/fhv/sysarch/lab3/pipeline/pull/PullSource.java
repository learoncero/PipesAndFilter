package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.ArrayList;
import java.util.List;

public class PullSource implements IPullFilter<Model, Face>{
    private List<Face> faces;

    public PullSource(Model model) {
        this.faces = model.getFaces();
    }

    @Override
    public void setPipePredecessor(IPullPipe<Model> pipePredecessor) {
        // no predecessor
    }

    @Override
    public Face read() {
        return !hasNext() ? null : faces.removeLast();
    }

    @Override
    public Face process(Model data) {
        return read();
    }

    @Override
    public boolean hasNext() {
        return !faces.isEmpty();
    }
}
