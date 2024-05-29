package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class Source implements IPushFilter<Model, Face> {

    private IPushPipe<Face> successor;

    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(Model data) {
        data.getFaces().forEach(face -> successor.write(face));
    }

    @Override
    public Face process(Model data) {
        // no processing
        throw new UnsupportedOperationException("PushSource does not support process operation.");
    }
}
