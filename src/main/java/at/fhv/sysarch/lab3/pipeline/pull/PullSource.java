package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.List;
import java.util.Optional;

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
    public Optional<Face> read() {
        return !hasNext() ? Optional.empty() : Optional.of(faces.remove(faces.size() - 1));
    }

    @Override
    public Face process(Model data) {
        // no processing
        throw new UnsupportedOperationException("PullSource does not support process operation.");
    }

    public boolean hasNext() {
        return !faces.isEmpty();
    }
}
