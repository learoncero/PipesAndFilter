package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

import java.util.Optional;

public class ModelViewTransformationFilter extends APullFilter<Face, Face>{
    private final PipelineData pd;
    private Mat4 rotationMatrix;

    public ModelViewTransformationFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Optional<Face> read() {
        Optional<Face> optionalFace = predecessor.read();
        if (optionalFace.isEmpty()) {
            return Optional.empty();
        } else {
            Face face = optionalFace.get();
            return Optional.ofNullable(process(face));
        }
    }

    @Override
    public Face process(Face data) {
        if (rotationMatrix == null) {
            throw new IllegalStateException("Rotation matrix not set.");
        }

        Mat4 modelTranslation = pd.getModelTranslation();
        Mat4 viewTransform = pd.getViewTransform();

        Mat4 transformationMatrix = viewTransform.multiply(modelTranslation).multiply(rotationMatrix);

        Face transformedFace = new Face(
                transformationMatrix.multiply(data.getV1()),
                transformationMatrix.multiply(data.getV2()),
                transformationMatrix.multiply(data.getV3()),
                transformationMatrix.multiply(data.getN1()),
                transformationMatrix.multiply(data.getN2()),
                transformationMatrix.multiply(data.getN3())
        );
        return transformedFace;
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }
}
