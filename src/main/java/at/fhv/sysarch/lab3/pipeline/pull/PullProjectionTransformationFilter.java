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
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

import java.util.Optional;

public class PullProjectionTransformationFilter implements IPullFilter<Pair<Face, Color>, Pair<Face, Color>>{
    private IPullPipe<Pair<Face, Color>> predecessor;
    private final PipelineData pd;

    public PullProjectionTransformationFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void setPipePredecessor(IPullPipe<Pair<Face, Color>> pipePredecessor) {
        this.predecessor = pipePredecessor;
    }

    @Override
    public Optional<Pair<Face, Color>> read() {
        Optional<Pair<Face, Color>> optionalPair = predecessor.read();
        if (optionalPair.isEmpty()) {
            return Optional.empty();
        } else {
            Pair<Face, Color> pair = optionalPair.get();
            return Optional.ofNullable(process(pair));
        }
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
