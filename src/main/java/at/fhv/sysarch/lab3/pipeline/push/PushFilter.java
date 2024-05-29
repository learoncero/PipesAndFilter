package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;

public class PushFilter implements IPushFilter<Face, Face> {

    private IPushPipe<Face> successor;


    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(Face data) {
        Face newFace = new Face(data.getV1().multiply(100), data.getV2().multiply(100), data.getV3().multiply(100), data);
        this.successor.write(newFace);
    }

    @Override
    public Face process(Face data) {
        return null;
    }
}
