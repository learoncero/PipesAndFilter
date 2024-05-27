package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;

public class BackfaceCullingFilter implements IPushFilter<Face, Face> {
    private IPushPipe<Face> successor;

    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(Face data) {
        Face face = process(data);
        if (face != null) {
            successor.write(face);
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
