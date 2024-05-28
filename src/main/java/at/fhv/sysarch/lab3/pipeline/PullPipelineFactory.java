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
        PullSource pullSource = new PullSource(pd.getModel());
        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        PullPipe<Face> modelViewToSourcePipe = new PullPipe<>();
        modelViewToSourcePipe.setFilterPredecessor(pullSource);
        PullModelViewTransformationFilter modelViewFilter = new PullModelViewTransformationFilter(pd);
        modelViewFilter.setPipePredecessor(modelViewToSourcePipe);
        // TODO 2. perform backface culling in VIEW SPACE
        PullPipe<Face> backfaceCullingToModelViewPipe = new PullPipe<>();
        backfaceCullingToModelViewPipe.setFilterPredecessor(modelViewFilter);
        PullBackfaceCullingFilter backfaceCullingFilter = new PullBackfaceCullingFilter();
        backfaceCullingFilter.setPipePredecessor(backfaceCullingToModelViewPipe);
        // TODO 3. perform depth sorting in VIEW SPACE


        // TODO 4. add coloring (space unimportant)
        PullPipe<Face> coloringToBackfaceCullingPipe = new PullPipe<>();
        coloringToBackfaceCullingPipe.setFilterPredecessor(backfaceCullingFilter);
        PullColourFilter colouringFilter = new PullColourFilter(pd);
        colouringFilter.setPipePredecessor(coloringToBackfaceCullingPipe);
        // lighting can be switched on/off
        PullProjectionTransformationFilter projectionFilter = new PullProjectionTransformationFilter(pd);

        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            PullPipe<Pair<Face, Color>> lightingToColouringPipe = new PullPipe<>();
            lightingToColouringPipe.setFilterPredecessor(colouringFilter);
            PullLightingFilter lightingFilter = new PullLightingFilter(pd);
            lightingFilter.setPipePredecessor(lightingToColouringPipe);
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
            PullPipe<Pair<Face, Color>> projectionToLightingPipe = new PullPipe<>();
            projectionToLightingPipe.setFilterPredecessor(lightingFilter);

            projectionFilter.setPipePredecessor(projectionToLightingPipe);
        } else {
            // 5. TODO perform projection transformation
            PullPipe<Pair<Face, Color>> projectionToColouringPipe = new PullPipe<>();
            projectionToColouringPipe.setFilterPredecessor(colouringFilter);
            projectionFilter.setPipePredecessor(projectionToColouringPipe);
        }

        // TODO 6. perform perspective division to screen coordinates
            PullPipe<Pair<Face, Color>> perspectiveToProjectionPipe = new PullPipe<>();
            perspectiveToProjectionPipe.setFilterPredecessor(projectionFilter);
            PullScreenSpaceTransformationFilter screenSpaceFilter = new PullScreenSpaceTransformationFilter(pd);
            screenSpaceFilter.setPipePredecessor(perspectiveToProjectionPipe);

        // TODO 7. feed into the sink (renderer)
            PullPipe<Pair<Face, Color>> sinkToScreenSpacePipe = new PullPipe<>();
            sinkToScreenSpacePipe.setFilterPredecessor(screenSpaceFilter);
            PullSink pullSink = new PullSink(pd.getGraphicsContext(), pd.getModelColor(), pd.getRenderingMode());
            pullSink.setPipePredecessor(sinkToScreenSpacePipe);
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

                // TODO trigger rendering of the pipeline
                pullSink.read();
            }
        };
    }
}