package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.Optional;

public class BackfaceCullingFilter implements IPullFilter<Face, Face> {
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
        float v1DotProduct = data.getV1().dot(data.getN1());
        float v2DotProduct = data.getV2().dot(data.getN2());
        float v3DotProduct = data.getV3().dot(data.getN3());

        if (v1DotProduct > 0 || v2DotProduct > 0 || v3DotProduct > 0) {
            return null;
        } else {
            return data;
        }
    }
}
