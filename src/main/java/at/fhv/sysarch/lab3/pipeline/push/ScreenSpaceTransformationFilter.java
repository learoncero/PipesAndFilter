package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class ScreenSpaceTransformationFilter implements IPushFilter<Pair<Face, Color>, Pair<Face, Color>> {
    private IPushPipe<Pair<Face, Color>> successor;
    private final PipelineData pd;

    public ScreenSpaceTransformationFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void setPipeSuccessor(IPushPipe<Pair<Face, Color>> pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(Pair<Face, Color> data) {
        Pair<Face, Color> pair = process(data);
        this.successor.write(pair);
    }

    @Override
    public Pair<Face, Color> process(Pair<Face, Color> data) {
        Mat4 viewportTransform = pd.getViewportTransform();
        Face face = data.fst();

        Face transformedFace = new Face(
                viewportTransform.multiply(face.getV1().multiply(1 / face.getV1().getW())),
                viewportTransform.multiply(face.getV2().multiply(1 / face.getV2().getW())),
                viewportTransform.multiply(face.getV3().multiply(1 / face.getV3().getW())),
                face
        );

        return new Pair<>(transformedFace, data.snd());
    }
}
