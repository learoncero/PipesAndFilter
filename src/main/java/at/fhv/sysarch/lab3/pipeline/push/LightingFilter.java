package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class LightingFilter extends APushFilter<Pair<Face, Color>, Pair<Face, Color>>{
    private PipelineData pd;

    public LightingFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Pair<Face, Color> data) {
        Pair<Face, Color> pair = process(data);
        this.successor.write(pair);
    }

    @Override
    public Pair<Face, Color> process(Pair<Face, Color> data) {
        if (!pd.isPerformLighting()) {
            return data;
        } else {
            Face face = data.fst();
            Color color = data.snd();

            float dotProduct = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());
            return new Pair<>(face, color.deriveColor(0, 1, dotProduct, 1));
        }
    }

}
