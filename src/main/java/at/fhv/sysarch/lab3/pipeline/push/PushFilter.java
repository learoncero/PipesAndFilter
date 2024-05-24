package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;

public class PushFilter implements IPushFilter<Face, Face> {

    private IPushPipe<Face> successor;


    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    public void write(Face face) {
        Face newFace = new Face(face.getV1().multiply(100), face.getV2().multiply(100), face.getV3().multiply(100), face);
        this.successor.write(newFace);
    }

    @Override
    public Face process(Face input) {
        return null;
    }
}
