package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

public class BackfaceCullingFilter implements IPullFilter<Face, Face> {
    private IPullPipe<Face> predecessor;

    @Override
    public void setPipePredecessor(IPullPipe<Face> pipePredecessor) {
        this.predecessor = pipePredecessor;
    }

    @Override
    public Face read() {
        Face face = predecessor.read();
        return process(face);
    }

    @Override
    public Face process(Face data) {
        return null;
    }

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }
}
