package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec4;

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
        if(optionalFace.isEmpty()) {
            return Optional.empty();
        }
        Face face = optionalFace.get();
        while(needsCulling(face)) {
            optionalFace = predecessor.read();
            if(optionalFace.isEmpty()) {
                return Optional.empty();
            }
            face = optionalFace.get();
        }
        return Optional.of(face);
    }

    @Override
    public Face process(Face data) {
        return data;
    }

    private boolean needsCulling(Face face) {
        float v1DotProduct = face.getV1().dot(face.getN1());
        float v2DotProduct = face.getV2().dot(face.getN2());
        float v3DotProduct = face.getV3().dot(face.getN3());

        return (v1DotProduct > 0 || v2DotProduct > 0 || v3DotProduct > 0);
    }
}
