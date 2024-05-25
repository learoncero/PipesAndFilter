package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Sink implements IPushFilter<Pair<Face, Color>, Pair<Face, Color>> {
    private final GraphicsContext gpc;
    private final Color modelColor;
    private final RenderingMode renderingMode;

    public Sink(GraphicsContext gpc, Color modelColor, RenderingMode renderingMode) {
        this.gpc = gpc;
        this.modelColor = modelColor;
        this.renderingMode = renderingMode;
    }


    @Override
    public void setPipeSuccessor(IPushPipe<Pair<Face, Color>> pipeSuccessor) {
        // No successor
    }

    @Override
    public void write(Pair<Face, Color> input) {
        process(input);
    }

    @Override
    public Pair<Face, Color> process(Pair<Face, Color> input) {
        Face face = input.fst();
        Color color = input.snd();

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

        return input;
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
