package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

public class PullModelViewTransformationFilter<I extends Face> extends Pull<I, Face> {
    private final PipelineData pipelineData;
    private Mat4 rotationMatrix;

    public PullModelViewTransformationFilter(IPull<I> predecessor, PipelineData pipelineData) {
        super(predecessor);
        this.pipelineData = pipelineData;
    }

    @Override
    public Face pull() {
        Face face = predecessor.pull();
        return rotateFace(face);
    }

    private Face rotateFace(Face face) {
        if (rotationMatrix == null) {
            throw new IllegalStateException("Rotation matrix not set.");
        }

        return new Face(
                rotationMatrix.multiply(face.getV1()),
                rotationMatrix.multiply(face.getV2()),
                rotationMatrix.multiply(face.getV3()),
                rotationMatrix.multiply(face.getN1()),
                rotationMatrix.multiply(face.getN2()),
                rotationMatrix.multiply(face.getN3())
        );
    }

    public void setRotationMatrix(Mat4 newRotation) {
        this.rotationMatrix = pipelineData.getViewTransform().multiply(pipelineData.getModelTranslation()).multiply(newRotation);
    }
}
