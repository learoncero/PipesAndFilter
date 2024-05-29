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

public class PullColourFilter implements IPullFilter<Face, Pair<Face, Color>> {
    private IPullPipe<Face> predecessor;
    private final PipelineData pd;

    public PullColourFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void setPipePredecessor(IPullPipe<Face> pipePredecessor) {
        this.predecessor = pipePredecessor;
    }

    @Override
    public Pair<Face, Color> read() {
        Face face = predecessor.read();
        return process(face);
    }

    @Override
    public Pair<Face, Color> process(Face data) {
        return new Pair<>(data, pd.getModelColor());
    }

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }
}
