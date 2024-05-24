package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;

public class ModelViewTransformationPushFilter implements IPushFilter<Face, Face> {

    private IPushPipe<Face> successor;
    private final PipelineData pd;
    private final Mat4 rotationMatrix;

    public ModelViewTransformationPushFilter(PipelineData pd) {
        this.pd = pd;
        this.rotationMatrix = Matrices.rotate(5f, pd.getModelRotAxis());
    }

    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(Face input) {
        Face face = process(input);
        this.successor.write(face);
    }

    @Override
    public Face process(Face face) {
        if (rotationMatrix == null) {
            throw new IllegalStateException("Rotation matrix not set.");
        }

        Mat4 modelTranslation = pd.getModelTranslation();
        Mat4 viewTransform = pd.getViewTransform();

        Mat4 transformationMatrix = viewTransform.multiply(modelTranslation).multiply(rotationMatrix);

        return new Face(
                transformationMatrix.multiply(face.getV1()),
                transformationMatrix.multiply(face.getV2()),
                transformationMatrix.multiply(face.getV3()),
                transformationMatrix.multiply(face.getN1()),
                transformationMatrix.multiply(face.getN2()),
                transformationMatrix.multiply(face.getN3())
        );
    }
}
