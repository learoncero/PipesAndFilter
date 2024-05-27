package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class ProjectionTransformationFilter implements IPushFilter<Pair<Face, Color>, Pair<Face, Color>>{
    private IPushPipe<Pair<Face, Color>> successor;
    private PipelineData pd;

    public ProjectionTransformationFilter(PipelineData pd) {
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
        Face face = data.fst();
        Mat4 projTransform = pd.getProjTransform();

        Face projectedFace = new Face(
                projTransform.multiply(face.getV1()),
                projTransform.multiply(face.getV2()),
                projTransform.multiply(face.getV3()),
                face
        );

        return new Pair<>(projectedFace, data.snd());
    }
}
