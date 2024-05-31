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

import java.util.Optional;

public class ColourFilter extends APullFilter<Face, Pair<Face, Color>> {
    private final PipelineData pd;

    public ColourFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Optional<Pair<Face, Color>> read() {
        Optional<Face> optionalPair = predecessor.read();
        if (optionalPair.isEmpty()) {
            return Optional.empty();
        } else {
            Face face = optionalPair.get();
            return Optional.ofNullable(process(face));
        }
    }

    @Override
    public Pair<Face, Color> process(Face data) {
        return new Pair<>(data, pd.getModelColor());
    }

}
