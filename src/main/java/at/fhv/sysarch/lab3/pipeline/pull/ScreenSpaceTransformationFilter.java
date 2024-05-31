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
import com.hackoeur.jglm.Vec4;

import java.util.Optional;

public class ScreenSpaceTransformationFilter extends APullFilter<Pair<Face, Color>, Pair<Face, Color>>{
    private final PipelineData pd;

    public ScreenSpaceTransformationFilter(PipelineData pd) {
        this.pd = pd;
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
