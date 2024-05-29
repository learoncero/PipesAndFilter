package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

import java.util.Optional;

public class PullModelViewTransformationFilter implements IPullFilter<Face, Face>{
    private IPullPipe<Face> predecessor;
    private PipelineData pd;
    private Mat4 rotationMatrix;

    public PullModelViewTransformationFilter(PipelineData pd) {
        this.pd = pd;
    }


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
        if (rotationMatrix == null) {
            throw new IllegalStateException("Rotation matrix not set.");
        }

        return new Face(
                rotationMatrix.multiply(data.getV1()),
                rotationMatrix.multiply(data.getV2()),
                rotationMatrix.multiply(data.getV3()),
                rotationMatrix.multiply(data.getN1()),
                rotationMatrix.multiply(data.getN2()),
                rotationMatrix.multiply(data.getN3())
        );
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }
}
