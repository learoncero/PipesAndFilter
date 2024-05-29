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
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PullSink implements IPullFilter<Pair<Face, Color>, Pair<Face, Color>> {
    private final GraphicsContext gpc;
    private final Color modelColor;
    private final RenderingMode renderingMode;
    private IPullPipe<Pair<Face, Color>> predecessor;

    public PullSink(GraphicsContext gpc, Color modelColor, RenderingMode renderingMode) {
        this.gpc = gpc;
        this.modelColor = modelColor;
        this.renderingMode = renderingMode;
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
        Face face = data.fst();
        Color color = data.snd();

        gpc.setStroke(color);
        gpc.setFill(color);

        if (renderingMode == RenderingMode.POINT) {
            renderPoint(face);
        } else {
            renderLine(face);
            if (renderingMode == RenderingMode.FILLED) {
                fillPolygon(face);
            }
        }

        return data;
    }

    @Override
    public boolean hasNext() {
        return predecessor.hasNext();
    }

    private void fillPolygon(Face face) {
        gpc.fillPolygon(new double[]{face.getV1().getX(), face.getV2().getX(), face.getV3().getX()},
                new double[]{face.getV1().getY(), face.getV2().getY(), face.getV3().getY()}, 3);
    }

    private void renderLine(Face face) {
        gpc.strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());
        gpc.strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV3().getX(), face.getV3().getY());
        gpc.strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV3().getX(), face.getV3().getY());
    }

    private void renderPoint(Face face) {
        gpc.setLineWidth(1);
        gpc.strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV1().getX(), face.getV1().getY());
        gpc.strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV2().getX(), face.getV2().getY());
        gpc.strokeLine(face.getV3().getX(), face.getV3().getY(), face.getV3().getX(), face.getV3().getY());
    }

}
