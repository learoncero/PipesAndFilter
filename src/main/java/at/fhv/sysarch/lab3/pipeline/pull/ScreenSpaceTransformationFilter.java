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
    public Pair<Face, Color> process(Pair<Face, Color> pair) {

        return new Pair<>(
                new Face(
                        applyTransformation(pair.fst().getV1()),
                        applyTransformation(pair.fst().getV2()),
                        applyTransformation(pair.fst().getV3()),
                        pair.fst()
                ),
                pair.snd()
        );
    }

    private Vec4 applyTransformation(Vec4 vec) {
        return pd.getViewportTransform().multiply(vec.multiply(1f / vec.getW()));
    }
}
