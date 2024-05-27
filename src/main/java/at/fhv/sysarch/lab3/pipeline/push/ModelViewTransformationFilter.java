package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

public class ModelViewTransformationFilter implements IPushFilter<Face, Face> {

    private IPushPipe<Face> successor;
    private final PipelineData pd;
    private Mat4 rotationMatrix;

    public ModelViewTransformationFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(Face data) {
        Face face = process(data);
        this.successor.write(face);
    }

    @Override
    public Face process(Face data) {
        if (rotationMatrix == null) {
            throw new IllegalStateException("Rotation matrix not set.");
        }

        Mat4 modelTranslation = pd.getModelTranslation();
        Mat4 viewTransform = pd.getViewTransform();

        Mat4 transformationMatrix = viewTransform.multiply(modelTranslation).multiply(rotationMatrix);

        return new Face(
                transformationMatrix.multiply(data.getV1()),
                transformationMatrix.multiply(data.getV2()),
                transformationMatrix.multiply(data.getV3()),
                transformationMatrix.multiply(data.getN1()),
                transformationMatrix.multiply(data.getN2()),
                transformationMatrix.multiply(data.getN3())
        );
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }
}
