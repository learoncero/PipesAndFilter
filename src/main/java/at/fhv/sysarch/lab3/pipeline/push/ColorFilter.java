package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class ColorFilter extends APushFilter<Face, Pair<Face, Color>> {
    private final PipelineData pd;

    public ColorFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Face data) {
        Pair<Face, Color> pair = process(data);
        this.successor.write(pair);
    }

    @Override
    public Pair<Face, Color> process(Face data) {
        return new Pair<>(data, pd.getModelColor());
    }
}
