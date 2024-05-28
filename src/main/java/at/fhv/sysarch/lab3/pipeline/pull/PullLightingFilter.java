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

public class PullLightingFilter implements IPullFilter<Pair<Face, Color>, Pair<Face, Color>> {
    private IPullPipe<Pair<Face, Color>> predecessor;
    private PipelineData pd;

    public PullLightingFilter(PipelineData pd) {
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
        if (!pd.isPerformLighting()) {
            return data;
        } else {
            Face face = data.fst();
            Color color = data.snd();

            float dotProduct = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());
            return new Pair<>(face, color.deriveColor(0, 1, dotProduct, 1));
        }
    }

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }

    /*





    }*/

}
