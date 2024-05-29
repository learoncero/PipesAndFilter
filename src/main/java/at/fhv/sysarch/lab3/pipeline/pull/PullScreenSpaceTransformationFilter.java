/*
 * Copyright (c) 2024 Sarah N
 *
 * Project Name:         cgpipeline
 * Description:
 *
 * Date of Creation/
 * Last Update:          28/05/2024
 */

package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.Pipe;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PullScreenSpaceTransformationFilter implements IPullFilter<Pair<Face, Color>, Pair<Face, Color>>{
    IPullPipe<Pair<Face, Color>> predecessor;
    private final PipelineData pd;

    public PullScreenSpaceTransformationFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void setPipePredecessor(IPullPipe<Pair<Face, Color>> pipePredecessor) {
        this.predecessor = pipePredecessor;
    }

    @Override
    public Pair<Face, Color> read() {
        Pair<Face, Color> pair = predecessor.read();
        return process(pair);
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

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }
}
