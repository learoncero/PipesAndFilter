package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class ColorFilter implements IPushFilter<Face, Pair<Face, Color>> {
    private IPushPipe<Pair<Face, Color>> successor;
    private PipelineData pd;

    public ColorFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void setPipeSuccessor(IPushPipe<Pair<Face, Color>> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(Face input) {
        Pair<Face, Color> pair = process(input);
        this.successor.write(pair);
    }

    @Override
    public Pair<Face, Color> process(Face input) {
        return new Pair<Face, Color>(input, pd.getModelColor());
    }
}
