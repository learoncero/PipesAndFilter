package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Source extends APullFilter<Model, Face>{
    private List<Face> faces;

    public Source(Model model) {
        this.faces = new ArrayList<>(model.getFaces());
    }

    @Override
    public void setPipePredecessor(IPullPipe<Model> pipePredecessor) {
        // no predecessor
    }

    public Optional<Face> read() {
        return !hasNext() ? Optional.empty() : Optional.ofNullable(faces.removeFirst());
    }

    @Override
    public Face process(Model data) {
        // no processing
        throw new UnsupportedOperationException("PullSource does not support process operation.");
    }

    public void setModel(Model model) {
        this.faces = new ArrayList<>(model.getFaces());
    }

    public boolean hasNext() {
        return !faces.isEmpty();
    }
}
