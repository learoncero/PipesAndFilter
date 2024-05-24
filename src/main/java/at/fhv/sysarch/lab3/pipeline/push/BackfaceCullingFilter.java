package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;

public class BackfaceCullingFilter implements IPushFilter<Face, Face> {
    private IPushPipe<Face> successor;

    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(Face input) {
        Face face = process(input);
        if (face != null) {
            successor.write(face);
        }
    }

    @Override
    public Face process(Face input) {
        float v1DotProduct = input.getV1().dot(input.getN1());
        float v2DotProduct = input.getV2().dot(input.getN2());
        float v3DotProduct = input.getV3().dot(input.getN3());

        if (v1DotProduct > 0 || v2DotProduct > 0 || v3DotProduct > 0) {
            return null;
        } else {
            return input;
        }
    }
}
