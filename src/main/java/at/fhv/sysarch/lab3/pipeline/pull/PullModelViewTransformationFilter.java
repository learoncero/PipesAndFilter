package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

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
    public Face read() {
        Face face = predecessor.read();
        return process(face);
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

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }
}
