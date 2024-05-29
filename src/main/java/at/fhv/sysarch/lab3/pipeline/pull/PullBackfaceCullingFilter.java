package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.Optional;

public class PullBackfaceCullingFilter implements IPullFilter<Face, Face> {
    private IPullPipe<Face> predecessor;

    @Override
    public void setPipePredecessor(IPullPipe<Face> pipePredecessor) {
        this.predecessor = pipePredecessor;
    }

    @Override
    public Optional<Face> read() {
        Optional<Face> optionalFace = predecessor.read();
        if (optionalFace.isEmpty()) {
            return Optional.empty();
        } else {
            Face face = optionalFace.get();
            return Optional.of(process(face));
        }
    }

    @Override
    public Face process(Face data) {
        return null;
    }
}
