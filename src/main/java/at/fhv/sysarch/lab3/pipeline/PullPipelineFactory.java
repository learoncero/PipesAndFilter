package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: pull from the source (model)
        Source source = new Source(pd.getModel());

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        Pipe<Face> modelViewToSourcePipe = new Pipe<>();
        modelViewToSourcePipe.setFilterPredecessor(source);
        ModelViewTransformationFilter modelViewFilter = new ModelViewTransformationFilter(pd);
        modelViewFilter.setPipePredecessor(modelViewToSourcePipe);

        // TODO 2. perform backface culling in VIEW SPACE
        Pipe<Face> backfaceCullingToModelViewPipe = new Pipe<>();
        backfaceCullingToModelViewPipe.setFilterPredecessor(modelViewFilter);
        BackfaceCullingFilter backfaceCullingFilter = new BackfaceCullingFilter();
        backfaceCullingFilter.setPipePredecessor(backfaceCullingToModelViewPipe);
        // TODO 3. perform depth sorting in VIEW SPACE


        // TODO 4. add coloring (space unimportant)

        Pipe<Face> coloringToBackfaceCullingPipe = new Pipe<>();
        coloringToBackfaceCullingPipe.setFilterPredecessor(backfaceCullingFilter);
        ColourFilter colouringFilter = new ColourFilter(pd);
        colouringFilter.setPipePredecessor(coloringToBackfaceCullingPipe);
        // lighting can be switched on/off
        ProjectionTransformationFilter projectionFilter = new ProjectionTransformationFilter(pd);

        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            Pipe<Pair<Face, Color>> lightingToColouringPipe = new Pipe<>();
            lightingToColouringPipe.setFilterPredecessor(colouringFilter);
            LightingFilter lightingFilter = new LightingFilter(pd);
            lightingFilter.setPipePredecessor(lightingToColouringPipe);
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
            Pipe<Pair<Face, Color>> projectionToLightingPipe = new Pipe<>();
            projectionToLightingPipe.setFilterPredecessor(lightingFilter);
            projectionFilter.setPipePredecessor(projectionToLightingPipe);
        } else {
            // 5. TODO perform projection transformation
            Pipe<Pair<Face, Color>> projectionToColouringPipe = new Pipe<>();
            projectionToColouringPipe.setFilterPredecessor(colouringFilter);
            projectionFilter.setPipePredecessor(projectionToColouringPipe);
        }

        // TODO 6. perform perspective division to screen coordinates
            Pipe<Pair<Face, Color>> perspectiveToProjectionPipe = new Pipe<>();
            perspectiveToProjectionPipe.setFilterPredecessor(projectionFilter);
            ScreenSpaceTransformationFilter screenSpaceFilter = new ScreenSpaceTransformationFilter(pd);
            screenSpaceFilter.setPipePredecessor(perspectiveToProjectionPipe);
        // TODO 7. feed into the sink (renderer)
            Pipe<Pair<Face, Color>> sinkToScreenSpacePipe = new Pipe<>();
            sinkToScreenSpacePipe.setFilterPredecessor(screenSpaceFilter);
            Sink sink = new Sink(pd.getGraphicsContext(), pd.getRenderingMode());
            sink.setPipePredecessor(sinkToScreenSpacePipe);

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
                double radiant = rotation % (2 * Math.PI );

                // TODO create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate
                Mat4 rotationMatrix = Matrices.rotate((float) radiant, pd.getModelRotAxis());

                // TODO compute updated model-view tranformation
                modelViewFilter.setRotationMatrix(rotationMatrix);

                // TODO update model-view filter
                source.setModel(model);
                // TODO trigger rendering of the pipeline
                while(sink.read().isPresent()){
                    sink.read();
                }
            }
        };
    }
}