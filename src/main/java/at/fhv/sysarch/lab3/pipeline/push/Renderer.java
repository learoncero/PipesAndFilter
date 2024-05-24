package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer implements IPushFilter<Face, Face> {

    private final GraphicsContext gpc;
    private final Color modelColor;
    private final RenderingMode renderingMode;

    public Renderer(GraphicsContext gpc, RenderingMode renderingMode, Color modelColor) {
        this.gpc = gpc;
        this.renderingMode = renderingMode;
        this.modelColor = modelColor;
    }

    @Override
    public void setPipeSuccessor(IPushPipe<Face> pipeSuccessor) {

    }

    @Override
    public void write(Face face) {
        gpc.setStroke(modelColor);
        if(this.renderingMode == RenderingMode.POINT) {
            gpc.setLineWidth(1);
            gpc.strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV1().getX(), face.getV1().getY());
            gpc.strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV2().getX(), face.getV2().getY());
            gpc.strokeLine(face.getV3().getX(), face.getV3().getY(), face.getV3().getX(), face.getV3().getY());
            gpc.setFill(modelColor);
        }
        else {
            gpc.strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());
            gpc.strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV3().getX(), face.getV3().getY());
            gpc.strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV3().getX(), face.getV3().getY());
        }
    }

    @Override
    public Face process(Face input) {
        // no need to implement

        return null;
    }
}
