package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.push.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;


public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: push from the source (model)
        Source source = new Source();

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        ModelViewTransformationFilter modelViewTransformationFilter = new ModelViewTransformationFilter(pd);
        Pipe<Face> toModelViewTransformationPipe = new Pipe<>();
        source.setPipeSuccessor(toModelViewTransformationPipe);
        toModelViewTransformationPipe.setFilterSuccessor(modelViewTransformationFilter);

        // TODO 2. perform backface culling in VIEW SPACE
        BackfaceCullingFilter backfaceCullingFilter = new BackfaceCullingFilter();
        Pipe<Face> toBackfaceCullingPipe = new Pipe<>();
        modelViewTransformationFilter.setPipeSuccessor(toBackfaceCullingPipe);
        toBackfaceCullingPipe.setFilterSuccessor(backfaceCullingFilter);

        // TODO 3. perform depth sorting in VIEW SPACE

        // TODO 4. add coloring (space unimportant)

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            // 5. TODO perform projection transformation
        }

        // TODO 6. perform perspective division to screen coordinates

        // TODO 7. feed into the sink (renderer)
        Pipe<Face> toResizeFilter = new Pipe<>();
        PushFilter resizeFilter = new PushFilter();
        backfaceCullingFilter.setPipeSuccessor(toResizeFilter);
        toResizeFilter.setFilterSuccessor(resizeFilter);

        Pipe<Face> toRenderer = new Pipe<>();
        Renderer renderer = new Renderer(pd.getGraphicsContext(), pd.getRenderingMode(), pd.getModelColor());
        resizeFilter.setPipeSuccessor(toRenderer);
        toRenderer.setFilterSuccessor(renderer);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            float rotation = 0f;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians
                rotation += fraction;
                double radiant = rotation % (2 * Math.PI);

                // TODO create new model rotation matrix using pd.modelRotAxis
                Mat4 rotationMatrix = Matrices.rotate((float) radiant, pd.getModelRotAxis());
                modelViewTransformationFilter.setRotationMatrix(rotationMatrix);

                // TODO trigger rendering of the pipeline
                source.write(model);
            }
        };
    }
}