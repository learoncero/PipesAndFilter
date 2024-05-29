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
        if (!hasNext()) {
            return null;
        } else {
            Face face = predecessor.read();
            return process(face);
        }
    }

    @Override
    public Face process(Face data) {
        float v1DotProduct = data.getV1().dot(data.getN1());
        float v2DotProduct = data.getV2().dot(data.getN2());
        float v3DotProduct = data.getV3().dot(data.getN3());

        if (v1DotProduct > 0 || v2DotProduct > 0 || v3DotProduct > 0) {
            return null;
        } else {
            return data;
        }
    }

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }
}
