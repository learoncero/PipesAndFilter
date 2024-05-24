package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class Source implements IPushFilter<Model, Face> {

    private IPushPipe<Face> successor;

    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    public void write(Model model) {
        model.getFaces().forEach(face -> successor.write(face));
    }

    @Override
    public Face process(Model input) {
        return null;
    }
}
